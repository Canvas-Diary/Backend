package com.canvas.openai.dalle;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class DalleClient {

    @Value("${openai.api-key}")
    private String API_KEY;

    private final WebClient webClient = WebClient.create("https://api.openai.com/v1/images/generations");

    public String generateImage(String prompt) {
        log.info("prompt={}", prompt);
        return Objects.requireNonNull(
                webClient.post()
                         .headers(httpHeaders -> {
                             httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                             httpHeaders.setBearerAuth(API_KEY);
                         })
                         .bodyValue(new Request(
                                 "dall-e-3",
                                 prompt,
                                 1,
                                 "1024x1792"
                         ))
                         .retrieve()
                         .bodyToMono(Response.class)
                         .block())
                      .getUrl();
    }

    public record Request(
            String model,
            String prompt,
            Integer n,
            String size
    ) {
    }

    public record Response(
            List<Data> data
    ) {
        public String getUrl() {
            log.info("url={}", data.get(0).url);
            return data.get(0).url;
        }

        public record Data(
                String url
        ) {
        }
    }

}