package com.canvas.application.diary.port.out;

import com.canvas.common.page.PageRequest;
import com.canvas.common.page.Slice;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface DiaryManagementPort {
    Diary save(Diary diary);
    Diary getPublicById(DomainId diaryId);
    Diary getByIdAndWriterId(DomainId diaryId, DomainId writerId);
    boolean existsByIdAndWriterId(DomainId diaryId, DomainId writerId);
    List<Diary> getByUserIdAndMonth(DomainId userId, LocalDate date);
    Slice<Diary> getAlbum(PageRequest pageRequest, DomainId userId);
    Slice<Diary> getAlbumByContent(PageRequest pageRequest, DomainId userId, String content);
    Slice<Diary> getAlbumByEmotion(PageRequest pageRequest, DomainId userId, Emotion emotion);
    Slice<Diary> getExploreByLatest(PageRequest pageRequest);
    Slice<Diary> getExploreByLike(PageRequest pageRequest);
    void deleteById(DomainId diaryId);
}
