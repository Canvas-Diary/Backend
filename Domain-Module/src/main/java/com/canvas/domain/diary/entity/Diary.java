package com.canvas.domain.diary.entity;

import com.canvas.domain.diary.vo.DiaryContent;
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
    private final List<Like> likes;
    private final Boolean isPublic;

    public static Diary withoutId(Long userId, DiaryContent diaryContent, LocalDate date, List<Like> likes, Boolean isPublic) {
        return new Diary(null, userId, diaryContent, date, likes, isPublic);
    }

    public static Diary withId(Long id, Long userId, DiaryContent diaryContent, LocalDate date, List<Like> likes, Boolean isPublic) {
        return new Diary(id, userId, diaryContent, date, likes, isPublic);
    }
}
