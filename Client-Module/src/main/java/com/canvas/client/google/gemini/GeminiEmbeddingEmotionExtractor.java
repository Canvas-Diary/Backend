package com.canvas.client.google.gemini;

import com.canvas.domain.diary.service.EmotionExtractor;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class GeminiEmbeddingEmotionExtractor implements EmotionExtractor {

    private final WebClient webClient = WebClient.create("https://generativelanguage.googleapis.com/v1beta/models/embedding-001:embedContent");
    private final GeminiProperties geminiProperties;

    @Override
    public String extractEmotion(EmotionExtractProcessingData data) {
        List<Double> embedding = getEmbedding(data.diaryDescription());
        GeminiEmotionEmbedding emotion = getNearestEmotion(embedding);
        return emotion.name().toLowerCase();
    }

    @Override
    public CompletableFuture<String> extractEmotionAsync(EmotionExtractProcessingData data) {
        return CompletableFuture.supplyAsync(() -> extractEmotion(data));
    }

    private List<Double> getEmbedding(String text) {
        return Objects.requireNonNull(webClient.post()
                .uri(uriBuilder -> uriBuilder.queryParam("key", geminiProperties.getApiKey()).build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new GeminiRequest(
                        "models/embedding-001",
                        new RequestContent(List.of(new Part(text))),
                        "classification"
                )).retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(GeminiResponse.class)
                .block())
                .embedding.values;
    }

    private GeminiEmotionEmbedding getNearestEmotion(List<Double> embedding) {
        TreeMap<Double, GeminiEmotionEmbedding> map = new TreeMap<>();
        GeminiEmotionEmbedding[] emotions = GeminiEmotionEmbedding.values();

        for (GeminiEmotionEmbedding emotion : emotions) {
            map.put(cosineSimilarity(embedding, emotion.getEmbedding()), emotion);
        }

        return map.firstEntry().getValue();
    }

    private Double cosineSimilarity(List<Double> l1, double[] l2) {
        double dot = 0;
        double sum1 = 0;
        double sum2 = 0;
        for (int i = 0; i < l1.size(); i++) {
            dot += l1.get(i) * l2[i];
            sum1 += l1.get(i) * l1.get(i);
            sum2 += l2[i] * l2[i];
        }
        return 1.0 - dot / (Math.sqrt(sum1) * Math.sqrt(sum2));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class GeminiRequest {
        private String model;
        private RequestContent content;
        private String taskType;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class RequestContent {
        private List<Part> parts;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class Part {
        private String text;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class GeminiResponse {
        private Embedding embedding;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class Embedding {
        private List<Double> values;
    }

}
