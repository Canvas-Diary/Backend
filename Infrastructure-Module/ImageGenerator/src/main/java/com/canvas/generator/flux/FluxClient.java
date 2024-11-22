package com.canvas.generator.flux;

import com.canvas.generator.flux.exception.FluxException;
import com.canvas.generator.flux.exception.FluxResultStatus;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class FluxClient {

    private static final Integer WIDTH = 768;
    private static final Integer HEIGHT = 1344;

    @Value("${flux.api-key}")
    private String API_KEY;

    private final WebClient webClient = WebClient.create("https://api.bfl.ml/v1");

    public String generateImage(String prompt) {
        log.info("prompt={}", prompt);
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
                .getId();
    }

    public String getResult(String id) {
        log.info("id={}", id);
        return Objects.requireNonNull(webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/get_result")
                        .queryParam("id", id)
                        .build())
                .retrieve()
                .bodyToMono(GetResponse.class)
                .map(response -> {
                    FluxResultStatus.parse(response.status).validate();
                    return response;
                })
                .retryWhen(
                        Retry.fixedDelay(3, Duration.ofSeconds(1))
                                .filter(throwable -> throwable instanceof FluxException.FluxPendingException))
                .block())
                .getSample();
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
        public String getId() {
            return id;
        }
    }

    public record GetResponse(
            String id,
            String status,
            Result result
    ) {
        public String getSample() {
            return result.sample;
        }

        public record Result(
                String sample,
                String prompt
        ) {
        }
    }

}
