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
import com.canvas.domain.diary.entity.DiaryComplete;
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
        if (diaryManagementPort.existsByWriterIdAndDate(DomainId.from(command.userId()), command.date())) {
            throw new DiaryException.DiaryBadRequestException();
        }

        DomainId diaryId = DomainId.generate();
        Emotion emotion = diaryEmotionExtractPort.emotionExtract(command.content());
        String joinedWeightedContents = String.join(",", command.weightedContents());
        Image image = createImage(diaryId, command.content(), joinedWeightedContents, command.style());

        diaryManagementPort.save(
                DiaryComplete.create(
                        diaryId,
                        DomainId.from(command.userId()),
                        command.content(),
                        command.weightedContents(),
                        emotion,
                        command.date(),
                        command.isPublic(),
                        image
                )
        );

        return new Response(diaryId.toString());
    }

    @Override
    public void modify(ModifyDiaryUseCase.Command command) {
        DiaryComplete diary = diaryManagementPort.getByIdAndWriterId(
                DomainId.from(command.diaryId()),
                DomainId.from(command.userId())
        );

        if (!command.content().equals(diary.getContent())) {
            Emotion emotion = diaryEmotionExtractPort.emotionExtract(command.content());
            diary.updateDiaryContent(command.content(), emotion);
        }

        diary.updateWeightedContents(command.weightedContents());
        diary.updatePublic(command.isPublic());

        diaryManagementPort.save(diary);
    }

    private Image createImage(DomainId diaryId, String content, String joinedWeightedContents, Style style) {
        String imageUrl = addImageUseCase.create(
                new AddImageUseCase.Command.Create(diaryId.toString(), content, joinedWeightedContents, style)).imageUrl();
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
