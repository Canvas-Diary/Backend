package com.canvas.bootstrap.diary.controller;

import com.canvas.application.common.enums.Style;
import com.canvas.application.diary.port.in.AddDiaryUseCase;
import com.canvas.application.diary.port.in.GetDiaryUseCase;
import com.canvas.application.diary.port.in.ModifyDiaryUseCase;
import com.canvas.application.diary.port.in.RemoveDiaryUseCase;
import com.canvas.bootstrap.diary.api.DiaryApi;
import com.canvas.bootstrap.diary.dto.*;
import com.canvas.bootstrap.diary.enums.ExploreOrder;
import com.canvas.bootstrap.diary.enums.SearchType;
import lombok.RequiredArgsConstructor;
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
    public ReadDiaryCalenderResponse readDiaryCalender(LocalDate date) {
        return null;
    }

    @Override
    public void updateDiary(String diaryId, UpdateDiaryRequest request) {

    }

    @Override
    public void deleteDiary(String diaryId) {

    }

    @Override
    public DiarySearchResponse searchDiary(SearchType type, Style value) {
        return null;
    }

    @Override
    public DiaryExploreResponse exploreDiary(ExploreOrder order) {
        return null;
    }
}
