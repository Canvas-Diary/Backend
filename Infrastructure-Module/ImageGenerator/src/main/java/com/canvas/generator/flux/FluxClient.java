package com.canvas.generator.flux;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FluxClient {

    private static final Integer WIDTH = 768;
    private static final Integer HEIGHT = 1344;

    @Value("${flux.api-key}")
    private String API_KEY;

    private final WebClient webClient = WebClient.create("https://api.bfl.ml/v1");

    // TODO - 예외 처리 필요
    public String generateImage(String prompt) {
        return Objects.requireNonNull(webClient.post()
                .uri("/flux-pro-1.1")
                .headers(httpHeaders -> {
                    httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    httpHeaders.add("X-Key", API_KEY);
                }).bodyValue(
                        new GenerateRequest(
                                prompt,
                                WIDTH,
                                HEIGHT,
                                true
                        )
                ).retrieve()
                .bodyToMono(GenerateResponse.class)
                .block())
                .id;
    }

    public String getResult(String id) {
        return Objects.requireNonNull(webClient.get()
                .uri("/get_result")
                .attribute("id", id)
                .retrieve()
                .bodyToMono(GetResponse.class)
                .block())
                .result
                .sample;
    }

    public record GenerateRequest(
            String prompt,
            Integer width,
            Integer height,
            Boolean promptUpsampling
    ) {
    }

    public record GenerateResponse(
            String id
    ) {
    }

    public record GetResponse(
            String id,
            String status,
            Result result
    ) {
        public record Result(
                String sample,
                String prompt
        ) {
        }
    }

}
