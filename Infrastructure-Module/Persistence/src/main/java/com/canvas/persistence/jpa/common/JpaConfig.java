package com.canvas.persistence.jpa.common;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {"com.canvas.persistence.jpa"})
@EntityScan(basePackages = {"com.canvas.persistence.jpa"})
public class JpaConfig {
}
