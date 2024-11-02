package com.canvas.application.diary.port.out;

import com.canvas.common.page.PageRequest;
import com.canvas.common.page.Slice;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryBasic;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.DiaryOverview;
import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface DiaryManagementPort {
    DiaryComplete save(DiaryComplete diary);
    boolean existsByWriterIdAndDate(DomainId userId, LocalDate date);
    DiaryComplete getPublicById(DomainId diaryId);
    DiaryComplete getByIdAndWriterId(DomainId diaryId, DomainId writerId);
    boolean existsByIdAndWriterId(DomainId diaryId, DomainId writerId);
    List<DiaryBasic> getByUserIdAndMonth(DomainId userId, LocalDate date);
    Slice<DiaryOverview> getAlbum(PageRequest pageRequest, DomainId userId);
    Slice<DiaryOverview> getAlbumByContent(PageRequest pageRequest, DomainId userId, String content);
    Slice<DiaryOverview> getAlbumByEmotion(PageRequest pageRequest, DomainId userId, Emotion emotion);
    Slice<DiaryOverview> getAlbumByContentAndEmotion(PageRequest pageRequest, DomainId userId, String content, Emotion emotion);
    Slice<DiaryOverview> getExploreByLatest(PageRequest pageRequest);
    Slice<DiaryOverview> getExploreByLike(PageRequest pageRequest);
    void deleteById(DomainId diaryId);
}
