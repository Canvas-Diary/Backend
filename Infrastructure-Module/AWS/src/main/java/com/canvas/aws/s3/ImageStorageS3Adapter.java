package com.canvas.aws.s3;

import com.canvas.application.image.port.out.ImageStoragePort;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageStorageS3Adapter implements ImageStoragePort {

    private final S3Template s3Template;
    private static final String BUCKET_NAME = "canvas-diary";
    private static final String CONTENT_TYPE = "image/jpg";
    private static final String EXTENSION = ".jpg";
    private final WebClient webClient = WebClient.builder().build();

    @Override
    //TODO 예외 처리, test 코드 작성
    public String upload(String imageUrl) {

        ByteArrayOutputStream byteArrayOutputStream = imageUrlToByteStream(imageUrl);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        String storageUrl = createImageName() + EXTENSION;

        S3Resource resource = s3Template.upload(
                BUCKET_NAME,
                storageUrl,
                byteArrayInputStream,
                ObjectMetadata.builder()
                        .contentType(CONTENT_TYPE)
                        .build()
        );

        return getUrlString(resource);
    }

    @Override
    public void delete(String imageUrl) {
        String[] split = imageUrl.split("/");
        String key = split[split.length - 1];

        log.info("key={}", key);

        s3Template.deleteObject(BUCKET_NAME, key);
    }

    private ByteArrayOutputStream imageUrlToByteStream(String imageUrl) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(imageUrl).build(true);

        Flux<DataBuffer> dataBufferFlux = webClient.get()
                .uri(uriComponents.toUri())
                .retrieve()
                .bodyToFlux(DataBuffer.class);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        DataBufferUtils.write(dataBufferFlux, byteArrayOutputStream)
                .doOnError((e) -> {
                    log.error(e.toString());
                    throw new S3Excpetion.S3UploadException();
                })
                .blockLast();

        return byteArrayOutputStream;
    }

    private String createImageName() {
        return UUID.randomUUID().toString();
    }

    private static String getUrlString(S3Resource resource) {
        try {
            return resource.getURL().toString();
        } catch (IOException e) {
            throw new S3Excpetion.S3UploadException();
        }
    }

}
