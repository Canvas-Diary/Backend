package com.canvas.bootstrap.diary.controller;

import com.canvas.bootstrap.diary.api.DiaryApi;
import com.canvas.bootstrap.diary.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/diaries")
@RequiredArgsConstructor
public class DiaryController implements DiaryApi {

//    private final AddDiaryUseCase addDiaryUseCase;
//    private final GetDiaryUseCase getDiaryUseCase;
//    private final ModifyDiaryUseCase modifyDiaryUseCase;
//    private final RemoveDiaryUseCase removeDiaryUseCase;

    @Override
    @PostMapping()
    public CreateDiaryResponse createDiary(CreateDiaryRequest request) {

//        AddDiaryUseCase.Response response = addDiaryUseCase.add(AddDiaryUseCase.Command());
//        return new CreateDiaryResponse(response.diaryId());
        return null;
    }

    @Override
    @GetMapping("/{diaryId}")
    public ReadDiaryResponse readDiary(String diaryId) {

        return null;
    }

    @Override
    @GetMapping()
    public ReadDiaryCalenderResponse readDiaryCalender(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") LocalDate date) {
        return null;
    }

    @Override
    @PatchMapping("/{diaryId}")
    public void updateDiary(@PathVariable String diaryId, @RequestBody UpdateDiaryRequest request) {

    }

    @Override
    @DeleteMapping("/{diaryId}")
    public void deleteDiary(@PathVariable String diaryId) {

    }
}
