package com.canvas.client.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3Properties properties;

    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials awsCredentials =
                new BasicAWSCredentials(properties.getCredentials().getAccessKey(), properties.getCredentials().getSecretKey());
        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(properties.getRegion().getStatic())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

}
