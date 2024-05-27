package com.canvas.client.aws;

import com.canvas.domain.diary.exception.DiaryException;
import com.canvas.domain.diary.service.ImageStorageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3ImageStorageHandler  implements ImageStorageHandler {
    private final S3Uploader s3Uploader;
    @Override
    public String storeImage(String imageUrl) {
        try{
            return s3Uploader.uploadImage(imageUrl);
        }catch (Exception e){
            throw new DiaryException.ImageStoreFailException();
        }
    }
}
