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
import com.canvas.domain.diary.vo.DiaryContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

        diaryManagementPort.save(
                Diary.create(
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

    private DiaryContent createDiaryContent(DomainId diaryId, String content, Style style) {
        AddImageUseCase.Response ImageInform = addImageUseCase.add(new AddImageUseCase.Command(diaryId.toString(), content, style));
        Image image = Image.create(
                DomainId.from(ImageInform.imageId()),
                diaryId,
                ImageInform.isMain(),
                ImageInform.imageUrl()
        );

        Emotion emotion = diaryEmotionExtractPort.emotionExtract(content);

        return DiaryContent.create(content, emotion, new ArrayList<>(List.of(image)));
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
