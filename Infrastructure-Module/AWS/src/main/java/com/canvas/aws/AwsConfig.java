package com.canvas.aws;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.canvas.aws"})
@EnableAutoConfiguration
public class AwsConfig {
}
