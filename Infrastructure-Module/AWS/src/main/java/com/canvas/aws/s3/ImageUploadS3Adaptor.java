package com.canvas.aws.s3;

import com.canvas.application.image.port.out.ImageUploadPort;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageUploadS3Adaptor implements ImageUploadPort {

    private S3Template s3Template;
    private static final String BUCKET_NAME = "canvas-diary";
    private static final String CONTENT_TYPE = "image/webp";
    private static final String EXTENSION = ".webp";
    private final WebClient webClient = WebClient.builder().build();

    @Override
    //TODO 예외 처리, test 코드 작성
    public String upload(String imageUrl) {

        ByteArrayOutputStream byteArrayOutputStream = urlToByteArray(imageUrl);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        String storageUrl = createImageName() + EXTENSION;

        try {
            S3Resource resource = s3Template.upload(
                    BUCKET_NAME,
                    storageUrl,
                    byteArrayInputStream,
                    ObjectMetadata.builder()
                            .contentType(CONTENT_TYPE)
                            .build());

            return resource.getURL().toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream urlToByteArray(String imageUrl) {

        Flux<DataBuffer> dataBufferFlux = webClient.get()
                .uri(imageUrl)
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        dataBufferFlux
            .doOnNext(dataBuffer -> {
                try {
                    byteArrayOutputStream.write(dataBuffer.asByteBuffer().array());
                } catch (IOException e) {
                    throw new RuntimeException("ByteArrayOutputStream 작성 중 오류 발생", e);
                }
            })
            .blockLast();

        return byteArrayOutputStream;
    }

    private String createImageName() {
        return UUID.randomUUID().toString();
    }

}
