package com.canvas.google.gemini.service;

import com.canvas.google.gemini.exception.GeminiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class GeminiService {

    @Value("${gemini.api-key}")
    private String API_KEY;

    private final WebClient webClient = WebClient.create("https://generativelanguage.googleapis.com/v1beta/models");

    public String generate(String prompt) {
        return Objects.requireNonNull(webClient.post()
                        .uri("/gemini-1.5-flash-latest:generateContent")
                        .attribute("key", API_KEY)
                        .headers(httpHeaders -> {
                            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                            httpHeaders.add("x-goog-api-key", API_KEY);
                        }).bodyValue(Request.of(prompt))
                        .retrieve()
                        .bodyToMono(Response.class)
                        .block())
                .getText()
                .trim();
    }

    public record Request(
            List<Content> contents
    ) {
        public static Request of(String prompt) {
            return new Request(List.of(new Content(List.of(new Content.Part(prompt)))));
        }

        public record Content(
                List<Part> parts
        ) {
            public record Part(
                    String text
            ) {
            }
        }
    }

    public record Response(
            List<Candidate> candidates
    ) {
        public String getText() {
            if (candidates.get(0).finishReason.equals("SAFETY")) {
                throw new GeminiException.GeminiSafetyException();
            }

            return candidates.get(0)
                    .content
                    .parts.get(0)
                    .text;
        }

        public record Candidate(
                Content content,
                String finishReason
        ) {
            public record Content(
                    List<Part> parts
            ) {
                public record Part(
                        String text
                ) {
                }
            }
        }
    }

}
