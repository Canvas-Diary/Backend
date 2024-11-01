package com.canvas.application.diary.service;

import com.canvas.application.common.enums.Style;
import com.canvas.application.diary.exception.DiaryException;
import com.canvas.application.diary.port.in.AddDiaryUseCase;
import com.canvas.application.diary.port.in.ModifyDiaryUseCase;
import com.canvas.application.diary.port.in.RemoveDiaryUseCase;
import com.canvas.application.diary.port.out.DiaryEmotionExtractPort;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.application.image.port.in.AddImageUseCase;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.enums.Emotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryCommandService
        implements AddDiaryUseCase, ModifyDiaryUseCase, RemoveDiaryUseCase {

    private final DiaryManagementPort diaryManagementPort;
    private final DiaryEmotionExtractPort diaryEmotionExtractPort;
    private final AddImageUseCase addImageUseCase;

    @Override
    public Response add(AddDiaryUseCase.Command command) {
        DomainId diaryId = DomainId.generate();
        Emotion emotion = diaryEmotionExtractPort.emotionExtract(command.content());
        Image image = createImage(diaryId, command.content(), command.style());

        diaryManagementPort.save(
                Diary.create(
                        diaryId,
                        DomainId.from(command.userId()),
                        command.content(),
                        emotion,
                        command.dateTime(),
                        command.isPublic(),
                        image
                )
        );

        return new Response(diaryId.toString());
    }

    @Override
    public void modify(ModifyDiaryUseCase.Command command) {
        DomainId diarId = DomainId.from(command.diaryId());

        Diary diary = diaryManagementPort.getByIdAndWriterId(
                diarId,
                DomainId.from(command.userId())
        );

        Emotion emotion = diaryEmotionExtractPort.emotionExtract(command.content());
        Image image = createImage(diarId, command.content(), command.style());

        diary.updateDiaryContent(command.content(), emotion, image);
        diary.updatePublic(command.isPublic());

        diaryManagementPort.save(diary);
    }

    private Image createImage(DomainId diaryId, String content, Style style) {
        String imageUrl = addImageUseCase.create(new AddImageUseCase.Command(diaryId.toString(), content, style)).imageUrl();
        return Image.create(DomainId.generate(), diaryId, true, imageUrl);
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

}
