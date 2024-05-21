package com.canvas.client.google.gemini;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@RequiredArgsConstructor
@Getter
@ConfigurationProperties(prefix = "key.gemini")
public class GeminiProperties {
    private final String apiKey;
}
