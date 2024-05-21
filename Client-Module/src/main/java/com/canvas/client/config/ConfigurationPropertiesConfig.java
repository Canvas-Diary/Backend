package com.canvas.client.config;

import com.canvas.client.google.gemini.GeminiProperties;
import com.canvas.client.openai.OpenApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({OpenApiProperties.class, GeminiProperties.class})
public class ConfigurationPropertiesConfig {
}
