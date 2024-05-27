package com.canvas.client.aws;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "cloud.aws")
public class S3Properties {
    private final S3 s3;
    private final Credentials credentials;
    private final Region region;

    @Getter
    @RequiredArgsConstructor
    public static class S3 {
        private final String bucket;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Region {
        private final String Static;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Credentials {
        private final String accessKey;
        private final String secretKey;
    }

}
