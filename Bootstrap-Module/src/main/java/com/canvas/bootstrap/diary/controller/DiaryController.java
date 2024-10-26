package com.canvas.bootstrap.diary.controller;

import com.canvas.application.diary.port.in.AddDiaryUseCase;
import com.canvas.application.diary.port.in.GetDiaryUseCase;
import com.canvas.application.diary.port.in.ModifyDiaryUseCase;
import com.canvas.application.diary.port.in.RemoveDiaryUseCase;
import com.canvas.bootstrap.diary.api.DiaryApi;
import com.canvas.bootstrap.diary.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController implements DiaryApi {

    private final AddDiaryUseCase addDiaryUseCase;
    private final GetDiaryUseCase getDiaryUseCase;
    private final ModifyDiaryUseCase modifyDiaryUseCase;
    private final RemoveDiaryUseCase removeDiaryUseCase;

    @Override
    public CreateDiaryResponse createDiary(CreateDiaryRequest request) {

//        AddDiaryUseCase.Response response = addDiaryUseCase.add(AddDiaryUseCase.Command());
//        return new CreateDiaryResponse(response.diaryId());
        return null;
    }

    @Override
    public ReadDiaryResponse readDiary(String diaryId) {

        return null;
    }

    @Override
    public ReadDiaryCalenderResponse readDiaryCalender(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") LocalDate date) {
        return null;
    }

    @Override
    public void updateDiary(@PathVariable String diaryId, @RequestBody UpdateDiaryRequest request) {

    }

    @Override
    public void deleteDiary(@PathVariable String diaryId) {

    }
}
