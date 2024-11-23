package com.canvas.application.image.service;

import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.application.image.exception.ImageException;
import com.canvas.application.image.port.in.AddImageUseCase;
import com.canvas.application.image.port.in.RemoveImageUseCase;
import com.canvas.application.image.port.in.SetMainImageUseCase;
import com.canvas.application.image.port.out.ImageGenerationPort;
import com.canvas.application.image.port.out.ImageManagementPort;
import com.canvas.application.image.port.out.ImagePromptGeneratePort;
import com.canvas.application.image.port.out.ImageUploadPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryComplete;
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

    private final DiaryManagementPort diaryManagementPort;
    private final ImageGenerationPort imageGenerationPort;
    private final ImageManagementPort imageManagementPort;
    private final ImagePromptGeneratePort imagePromptGeneratePort;
    private final ImageUploadPort imageUploadPort;

    // 이미지를 생성하고 저장하면 add
    @Override
    public Response.Add add(AddImageUseCase.Command.Add command) {
        DomainId diaryId = DomainId.from(command.diaryId());
        DiaryComplete diary = diaryManagementPort.getById(diaryId);

        Response.Create create = create(
                new AddImageUseCase.Command.Create(
                        command.diaryId(),
                        diary.getContent(),
                        diary.getJoinedWeightedContents(),
                        command.style()
                ));

        Image image = Image.create(DomainId.generate(),
                                   diaryId, !diary.hasImage(), create.imageUrl());

        imageManagementPort.save(image);

        return new Response.Add(image.getId().toString(), image.getIsMain(), image.getImageUrl());
    }

    // 이미지를 생성하기만 하면 create
    @Override
    public Response.Create create(AddImageUseCase.Command.Create command) {
//        String prompt = imagePromptGeneratePort.generatePrompt(command.content(), command.joinedWeightedContents());
//        String generatedImageUrl = imageGenerationPort.generate(prompt, command.style());
//        String uploadedImageUrl = imageUploadPort.upload(generatedImageUrl);
        String uploadedImageUrl = Image.DEFAULT_IMAGE_URL;
        return new Response.Create(uploadedImageUrl);
    }

    @Override
    public void remove(RemoveImageUseCase.Command command) {
        if (!imageManagementPort.existsByIdAndUserId(
                DomainId.from(command.imageId()),
                DomainId.from(command.userId()))) {
            throw new ImageException.ImageNotFoundException();
        }

        imageManagementPort.deleteById(DomainId.from(command.imageId()));
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
