package com.canvas.application.keyword.port.out;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.keyword.DiaryKeyword;

import java.time.LocalDate;
import java.util.List;

public interface DiaryKeywordManagementPort {
    void saveAllDiaryKeywords(List<DiaryKeyword> diaryKeywords);
    List<DiaryKeyword> findByWriteIdAndBetween(DomainId userId, LocalDate start, LocalDate end);
}
