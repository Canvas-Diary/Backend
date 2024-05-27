package com.canvas.client.config;

import com.canvas.client.google.gemini.GeminiProperties;
import com.canvas.client.openai.OpenApiProperties;
import com.canvas.client.aws.S3Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({OpenApiProperties.class, GeminiProperties.class, S3Properties.class})
public class ConfigurationPropertiesConfig {
}
