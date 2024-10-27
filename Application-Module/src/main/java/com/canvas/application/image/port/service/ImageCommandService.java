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
    public Response add(AddImageUseCase.Command command) {
        String prompt = imagePromptGeneratePort.generatePrompt(command.content());
        String generatedImageUrl = imageGenerationPort.generate(prompt, command.style());
        String uploadedImageUrl = imageUploadPort.upload(generatedImageUrl);

        Image image = Image.create(DomainId.generate(), DomainId.from(command.diaryId()), true, uploadedImageUrl);

        imageManagementPort.save(image);

        return new Response(image.getId().toString(), image.getIsMain(), image.getImageUrl());
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
