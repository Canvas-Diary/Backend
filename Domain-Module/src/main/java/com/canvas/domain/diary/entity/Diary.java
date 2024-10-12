package com.canvas.domain.diary.entity;

import com.canvas.domain.diary.vo.DiaryContent;
import com.canvas.domain.diary.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class Diary {
    private final Long id;
    private final Long userId;
    private final DiaryContent diaryContent;
    private final LocalDate date;
    private final Emotion emotion;
    private final List<Long> likeUserIds;
    private final Boolean isPublic;

    public Diary withoutId(Long userId, DiaryContent diaryContent, LocalDate date, Emotion emotion, List<Long> likeUserIds, Boolean isPublic) {
        return new Diary(null, userId, diaryContent, date, emotion, likeUserIds, isPublic);
    }

    public Diary withId(Long id, Long userId, DiaryContent diaryContent, LocalDate date, Emotion emotion, List<Long> likeUserIds, Boolean isPublic) {
        return new Diary(id, userId, diaryContent, date, emotion, likeUserIds, isPublic);
    }
}
