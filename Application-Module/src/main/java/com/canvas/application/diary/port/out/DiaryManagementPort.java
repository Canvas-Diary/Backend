package com.canvas.application.diary.port.out;

import com.canvas.domain.diary.entity.Diary;

import java.time.LocalDate;
import java.util.List;

public interface DiaryManagementPort {
    Diary save(Diary diary);
    Diary getById(Long diaryId);
    List<Diary> getByUserIdAndMonth(Long userId, LocalDate date);
    void delete(Diary diary);
}
