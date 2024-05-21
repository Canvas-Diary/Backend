package com.canvas.client.openai;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "key.openai")
public class OpenApiProperties {
    private final String apiKey;
}
