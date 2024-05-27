package com.canvas.api.diary.application;

import com.canvas.api.diary.application.dto.DiaryCreate;
import com.canvas.api.diary.application.dto.DiarySearch;
import com.canvas.api.diary.application.dto.ImageGenerate;
import com.canvas.domain.diary.model.Diary;
import com.canvas.domain.diary.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiaryService {

    private final CanvasConvertor canvasConvertor;
    private final EmotionExtractor emotionExtractor;
//    private final Translator translator;
    private final DiaryRemover diaryRemover;
    private final DiaryReader diaryReader;
    private final ImageStorageHandler imageStorageHandler;



    public ImageGenerate.Response generateImages(ImageGenerate.Request request) {
//        String translatedDescription =
//                translator.translateKoreanToEnglish(new TranslatorProcessingData(request.description()));
        String translatedDescription = request.description();
        var emotion = emotionExtractor.extractEmotionAsync(new EmotionExtractor.EmotionExtractProcessingData(translatedDescription));
        List<String> canvasImages =
                canvasConvertor.convertDiaryToCanvas(new CanvasConvertor.CanvasConvertProcessingData(translatedDescription, request.emotion(), request.style()));

        return ImageGenerate.convertToGeneratedImageToResponse(canvasImages);
    }

    public void saveDiary(DiaryCreate.Request request) {
        String storedImageUrl = imageStorageHandler.storeImage(request.imageUrl());
        Diary diary = Diary.of(request.content(), request.emotion(), storedImageUrl);
    }

    public DiarySearch.Response readDiaries(DiarySearch.Request request) {
        List<Diary> diaries = diaryReader.readByDateRangeAndContentAndEmotion(
                request.from(), request.to(), request.content(), request.emotion()
        );
        return DiarySearch.convertToDiaryReadResponse(diaries);
    }

    public void deleteDiary(Long diaryId) {
        diaryRemover.removeDiary(diaryId);
    }

}
