package com.canvas.application.diary.service;

import com.canvas.application.common.enums.Style;
import com.canvas.application.diary.exception.DiaryException;
import com.canvas.application.diary.port.in.AddDiaryUseCase;
import com.canvas.application.diary.port.in.ModifyDiaryUseCase;
import com.canvas.application.diary.port.in.RemoveDiaryUseCase;
import com.canvas.application.diary.port.out.DiaryEmotionExtractPort;
import com.canvas.application.image.port.out.ImageGenerationPort;
import com.canvas.application.image.port.out.ImagePromptGeneratePort;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.application.image.port.out.ImageUploadPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.domain.diary.vo.DiaryContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryCommandService
        implements AddDiaryUseCase, ModifyDiaryUseCase, RemoveDiaryUseCase {

    private final DiaryManagementPort diaryManagementPort;
    private final DiaryEmotionExtractPort diaryEmotionExtractPort;
    private final ImagePromptGeneratePort imagePromptGeneratePort;
    private final ImageGenerationPort imageGenerationPort;
    private final ImageUploadPort imageUploadPort;

    @Override
    public Response add(AddDiaryUseCase.Command command) {
        DomainId diaryId = DomainId.generate();

        diaryManagementPort.save(
                new Diary(
                        diaryId,
                        DomainId.from(command.userId()),
                        createDiaryContent(diaryId, command.content(), command.style()),
                        command.dateTime(),
                        command.isPublic()
                )
        );

        return new Response(diaryId.toString());
    }

    @Override
    public void modify(ModifyDiaryUseCase.Command command) {
        Diary diary = diaryManagementPort.getByIdAndWriterId(
                DomainId.from(command.diaryId()),
                DomainId.from(command.userId())
        );

        DiaryContent diaryContent = createDiaryContent(diary.getId(), command.content(), command.style());

        diary.updateDiaryContent(diaryContent);
        diary.updatePublic(command.isPublic());

        diaryManagementPort.save(diary);
    }

    @Override
    public void remove(RemoveDiaryUseCase.Command command) {
        if (!diaryManagementPort.existsByIdAndWriterId(
                DomainId.from(command.diaryId()),
                DomainId.from(command.userId())
        )) {
            throw new DiaryException.DiaryForbiddenException();
        }

        diaryManagementPort.deleteById(DomainId.from(command.diaryId()));
    }

    private DiaryContent createDiaryContent(DomainId diaryId, String content, Style style) {
        String prompt = imagePromptGeneratePort.generatePrompt(content);
        String imageUrl = imageGenerationPort.generate(prompt, style);
        String s3Url = imageUploadPort.upload(imageUrl);

        Emotion emotion = diaryEmotionExtractPort.emotionExtract(content);
        Image image = new Image(DomainId.generate(), diaryId, true, s3Url);

        return new DiaryContent(content, emotion, List.of(image));
    }

}
