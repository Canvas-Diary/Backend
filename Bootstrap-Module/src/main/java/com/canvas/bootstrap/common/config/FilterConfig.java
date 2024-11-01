package com.canvas.bootstrap.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FilterConfig {

    @Bean
    public List<String> excludedAuthUrls() {
        return List.of("/api/v1/auth/**");
    }
}
