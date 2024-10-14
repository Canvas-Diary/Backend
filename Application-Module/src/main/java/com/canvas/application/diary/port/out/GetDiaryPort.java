package com.canvas.application.diary.port.out;

import com.canvas.domain.diary.entity.Diary;

import java.time.LocalDate;
import java.util.List;

public interface GetDiaryPort {
    Diary getById(Long diaryId);
    List<Diary> getByUserIdAndDate(Long userId, LocalDate date);
}
