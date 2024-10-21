package com.canvas.diary.controller;

import com.canvas.application.diary.port.in.AddDiaryUseCase;
import com.canvas.application.diary.port.in.GetDiaryUseCase;
import com.canvas.application.diary.port.in.ModifyDiaryUseCase;
import com.canvas.application.diary.port.in.RemoveDiaryUseCase;
import com.canvas.diary.api.DiaryApi;
import com.canvas.diary.dto.CreateDiaryRequest;
import com.canvas.diary.dto.CreateDiaryResponse;
import com.canvas.diary.dto.ReadDiaryCalenderResponse;
import com.canvas.diary.dto.ReadDiaryResponse;
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
    public void updateDiary(@PathVariable String diaryId) {

    }

    @Override
    @PatchMapping("/{diaryId}")
    public void deleteDiary(@PathVariable String diaryId) {

    }
}
