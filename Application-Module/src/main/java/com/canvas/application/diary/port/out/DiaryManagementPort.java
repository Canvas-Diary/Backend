package com.canvas.application.diary.port.out;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;

import java.time.LocalDate;
import java.util.List;

public interface DiaryManagementPort {
    Diary save(Diary diary);
    Diary getByIdAndWriterId(DomainId diaryId, DomainId writerId);
    boolean existsByIdAndWriterId(DomainId diaryId, DomainId writerId);
    List<Diary> getByUserIdAndMonth(DomainId userId, LocalDate date);
    void deleteById(DomainId diaryId);
}
