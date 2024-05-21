package com.canvas.api.diary.presentation;

import com.canvas.api.diary.application.DiaryService;
import com.canvas.api.diary.application.dto.DiaryCreate;
import com.canvas.api.diary.application.dto.DiarySearch;
import com.canvas.api.diary.application.dto.ImageGenerate;
import com.canvas.domain.diary.model.Emotion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping(DiaryApi.DIARY_IMAGES)
    public ImageGenerate.Response generateImages(@RequestBody ImageGenerate.Request request) {
        return diaryService.generateImages(request);
    }

    @PostMapping(DiaryApi.BASE_URL)
    public void createdDiary(@RequestBody DiaryCreate.Request request) {
        diaryService.saveDiary(request);
    }

    @GetMapping(DiaryApi.BASE_URL)
    public DiarySearch.Response readDiaries(@RequestParam(required = false) LocalDate from,
                                            @RequestParam(required = false) LocalDate to,
                                            @RequestParam(required = false) String content,
                                            @RequestParam(required = false) Emotion emotion) {
        return diaryService.readDiaries(DiarySearch.convertToRequest(
                from, to, content, emotion
        ));
    }

    @DeleteMapping(DiaryApi.SPECIFIC_DIARY)
    public void deleteDiary(@PathVariable Long diaryId) {
        diaryService.deleteDiary(diaryId);
    }

}
