package com.canvas.client.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3 s3Client;
    private final S3Properties s3Properties;
    private static final String EXTENSION = "png";
    private static final String CONTENT_TYPE = "image/png";
    private static final String DIRECTORY = "diary/";


    public String uploadImage(String imageUrl) {
//        throw new RuntimeException("test");
        String saveImageName = DIRECTORY + createImageName() + "." + EXTENSION;

        ByteArrayOutputStream byteArrayOutputStream = urlToByteArray(imageUrl);
        int size = byteArrayOutputStream.size();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(CONTENT_TYPE);

        s3Client.putObject(s3Properties.getS3().getBucket(), saveImageName, byteArrayInputStream, objectMetadata);



        return s3Client.getUrl(s3Properties.getS3().getBucket(), saveImageName).toString();

    }

    // 예외 처리 필요
    private ByteArrayOutputStream urlToByteArray(String imageUrl) {

        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.GET.name());

            InputStream inputStream = connection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] image = new byte[4096];
            int byteRead;

            while((byteRead = inputStream.read(image)) != -1) {
                byteArrayOutputStream.write(image, 0 , byteRead);
            }

            return byteArrayOutputStream;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String createImageName() {
        return UUID.randomUUID().toString();
    }
}
