package com.canvas;

import com.canvas.application.ApplicationConfig;
import com.canvas.aws.AwsConfig;
import com.canvas.google.GoogleConfig;
import com.canvas.persistence.PersistenceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(
        value = {
                ApplicationConfig.class,
                AwsConfig.class,
                PersistenceConfig.class,
                GoogleConfig.class
        }
)
public class BootstrapApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootstrapApplication.class, args);
    }
}
