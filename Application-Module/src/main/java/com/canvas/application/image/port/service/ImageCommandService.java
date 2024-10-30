package com.canvas.application.image.port.service;

import com.canvas.application.image.port.exception.ImageException;
import com.canvas.application.image.port.in.AddImageUseCase;
import com.canvas.application.image.port.in.RemoveImageUseCase;
import com.canvas.application.image.port.in.SetMainImageUseCase;
import com.canvas.application.image.port.out.ImageGenerationPort;
import com.canvas.application.image.port.out.ImageManagementPort;
import com.canvas.application.image.port.out.ImagePromptGeneratePort;
import com.canvas.application.image.port.out.ImageUploadPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageCommandService
        implements AddImageUseCase, RemoveImageUseCase, SetMainImageUseCase {

    private final ImageGenerationPort imageGenerationPort;
    private final ImageManagementPort imageManagementPort;
    private final ImagePromptGeneratePort imagePromptGeneratePort;
    private final ImageUploadPort imageUploadPort;

    @Override
    public Response.add add(AddImageUseCase.Command command) {
        Response.create create = create(command);
        Image image = Image.create(DomainId.generate(), DomainId.from(command.diaryId()), true, create.imageUrl());

        imageManagementPort.save(image);

        return new Response.add(image.getId().toString(), image.getIsMain(), image.getImageUrl());
    }

    @Override
    public Response.create create(AddImageUseCase.Command command) {
//        String prompt = imagePromptGeneratePort.generatePrompt(command.content());
//        String generatedImageUrl = imageGenerationPort.generate(prompt, command.style());
//        String uploadedImageUrl = imageUploadPort.upload(generatedImageUrl);
        String uploadedImageUrl = imageUploadPort.upload("https://canvas-diary.s3.ap-northeast-2.amazonaws.com/9f7b3157-85b6-46d0-8198-b05739597522.jpg");

        return new Response.create(uploadedImageUrl);
    }

    @Override
    public void remove(RemoveImageUseCase.Command command) {
        if (!imageManagementPort.existsByIdAndUserId(
                DomainId.from(command.imageId()),
                DomainId.from(command.userId()))) {
            throw new ImageException.ImageNotFoundException();
        }

        imageManagementPort.deleteById(DomainId.from(command.diaryId()));
    }

    @Override
    public void setMain(SetMainImageUseCase.Command command) {
        DomainId imageId = DomainId.from(command.imageId());
        DomainId userId = DomainId.from(command.userId());

        List<Image> images = imageManagementPort.getAllInDiaryByIdAndWriterId(imageId, userId);

        if (images.isEmpty()) {
            throw new ImageException.ImageNotFoundException();
        }

        images.forEach(image -> image.updateMain(image.getId().equals(imageId)));
        imageManagementPort.saveAll(images);
    }

}
