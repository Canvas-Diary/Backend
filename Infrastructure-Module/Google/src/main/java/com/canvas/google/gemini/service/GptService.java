package com.canvas.google.gemini.service;

import com.canvas.google.gemini.exception.GptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Primary
@Component
@Slf4j
public class GptService implements LLMService{

    @Value("${openai.api-key}")
    private String apiKey;

    private final WebClient webClient = WebClient.create("https://api.openai.com/v1/chat/completions");

    public String generate(String prompt) {
        Response response = webClient.post()
                .uri("")
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .bodyValue(Request.of(prompt))
                .retrieve()
                .bodyToMono(Response.class)
                .doOnNext(res -> {
                    log.info(res.getReason());
                    if (res.getReason().equals("null")) {
                        throw new NullPointerException("응답 오류");
                    }
                    else if (!res.getReason().equals("stop")) {
                        throw new GptException();
                    }
                })
                .retryWhen(
                        Retry.fixedDelay(3, Duration.ofSeconds(3))
                                .filter(throwable -> throwable instanceof NullPointerException)
                )
                .doOnError(error -> {
                    if (error instanceof NullPointerException) {
                        throw new GptException.GptTooManyRequestsException();
                    }
                    throw new GptException();
                })
                .block();
        log.info("{}", response.usage().total_tokens());
        return response.getContent();
    }

    public record Request(
            String model,
            List<Message> messages,
            Double temperature,
            Integer max_tokens
    ) {
        public static Request of(String prompt) {
            return new Request(
                    "gpt-4o-mini",
                    List.of(
                            new Message("user", prompt)
                    ),
                    0.7,
                    300
            );
        }

        public record Message(String role, String content) {}
    }

    public record Response(
            List<Choice> choices,
            Usage usage
    ) {

        public String getContent() {
            return choices.get(0).message().content().trim();
        }
        public String getReason() {
            return choices.get(0).finish_reason();
        }

        public record Choice(
                Message message,
                String finish_reason            //  stop: 정상, length: 토큰 초과, content_filter: 부적절, null: 모델 오류
        ) {
            public record Message(
                    String content
            ) {}
        }
        public record Usage(
                Integer total_tokens
        ) {}
    }
}
