package com.canvas.client.openai.dalle;

import com.canvas.client.openai.OpenApiProperties;
import com.canvas.domain.diary.service.CanvasConvertor;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DallECanvasConvertor implements CanvasConvertor {

    private static final String HEIGHT = "1024";
    private static final String WIDTH = "1024";
    private static final String RESPONSE_TYPE = "url";
    private final WebClient webClient = WebClient.create("https://api.openai.com/v1/images/generations");

    private final OpenApiProperties openApiProperties;

    @Override
    public List<String> convertDiaryToCanvas(CanvasConvertProcessingData data) {
        return Objects.requireNonNull(webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + openApiProperties.getApiKey())
                .bodyValue(new DallERequest(
                        "dall-e-3",
                        String.format(DallEPromptConsts.DALLE_IMAGE_GENERATE_PROMPT, data.style(), data.emotion().name(), data.diaryDescription()),
                        1,
                        HEIGHT + "x" + WIDTH,
                        RESPONSE_TYPE
                )).retrieve()
                .bodyToMono(DallEResponse.class)
                .block())
                .extractImageUrls();
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class DallERequest {
        private String model;
        private String prompt;
        private int n;
        private String size;
        private String responseFormat;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    private static class DallEResponse {
        private String created;
        private List<DallEImageResponse> data;

        public List<String> extractImageUrls() {
            return this.data.stream()
                    .map(imageResponse -> imageResponse.url)
                    .toList();
        }
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class DallEImageResponse {
        private String url;
        private String revisedPrompt;
    }

}
