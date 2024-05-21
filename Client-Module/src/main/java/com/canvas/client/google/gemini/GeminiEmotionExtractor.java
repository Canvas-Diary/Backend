package com.canvas.client.google.gemini;

import com.canvas.domain.diary.service.EmotionExtractor;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

//@Component
@RequiredArgsConstructor
public class GeminiEmotionExtractor implements EmotionExtractor {

    private final WebClient webClient = WebClient.create(
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent");

    private final GeminiProperties geminiProperties;


    @Override
    public String extractEmotion(EmotionExtractProcessingData data) {
        return Objects.requireNonNull(webClient.post()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new GeminiRequest(List.of(new RequestContent(List.of(new Part(
                                GeminiPromptConsts.EMOTION_EXTRACT_PROMPT + data.diaryDescription()))))
                        )).retrieve()
                        .onStatus(
                                HttpStatus.BAD_REQUEST::equals,
                                response -> response.bodyToMono(String.class).map(Exception::new)
                        )
                        .bodyToMono(GeminiResponse.class)
                        .block())
                .candidates.get(0).content.parts.get(0).text; // 디미터의 법칙 위반
    }

    @Override
    public CompletableFuture<String> extractEmotionAsync(EmotionExtractProcessingData data) {
        return CompletableFuture.supplyAsync(() -> extractEmotion(data));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class GeminiRequest {
        private List<RequestContent> contents;
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
    private static class GeminiResponse{
        private List<Candidate> candidates;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class Candidate{
        private ResponseContent content;
        private String finishReason;
        private Long index;
        private List<SafetyRating> safetyRatings;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class ResponseContent{
        private List<Part> parts;
        private String role;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    private static class SafetyRating{
        private String category;
        private String probability;
    }

}
