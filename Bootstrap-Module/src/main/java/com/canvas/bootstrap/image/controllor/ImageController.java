package com.canvas.bootstrap.image.controllor;

import com.canvas.application.image.port.in.AddImageUseCase;
import com.canvas.application.image.port.in.RemoveImageUseCase;
import com.canvas.application.image.port.in.SetMainImageUseCase;
import com.canvas.bootstrap.image.api.ImageApi;
import com.canvas.bootstrap.image.dto.CreateImageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageController implements ImageApi {

    private final AddImageUseCase addImageUseCase;
    private final SetMainImageUseCase setMainImageUseCase;
    private final RemoveImageUseCase removeImageUseCase;

    @Override
    public void createImage(String userId, String diaryId, CreateImageRequest createImageRequest) {
        addImageUseCase.add(
                new AddImageUseCase.Command.Add(diaryId, createImageRequest.style()));
    }

    @Override
    public void deleteImage(String userId, String diaryId, String imageId) {
        removeImageUseCase.remove(
                new RemoveImageUseCase.Command(userId, diaryId, imageId));
    }

    @Override
    public void updateMainImage(String userId, String diaryId, String imageId) {
        setMainImageUseCase.setMain(
                new SetMainImageUseCase.Command(userId, imageId));
    }
}
