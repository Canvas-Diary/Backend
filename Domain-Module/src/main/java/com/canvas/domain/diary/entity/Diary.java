package com.canvas.domain.diary.entity;

import com.canvas.domain.diary.vo.DiaryContent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Diary {
    private final Long id;
    private final Long writerId;
    private final DiaryContent diaryContent;
    private final LocalDateTime dateTime;
    private final List<Like> likes;
    private final Boolean isPublic;

    public static Diary withoutId(Long userId, DiaryContent diaryContent, LocalDateTime datetime, List<Like> likes, Boolean isPublic) {
        return new Diary(null, userId, diaryContent, datetime, likes, isPublic);
    }

    public static Diary withId(Long id, Long userId, DiaryContent diaryContent, LocalDateTime datetime, List<Like> likes, Boolean isPublic) {
        return new Diary(id, userId, diaryContent, datetime, likes, isPublic);
    }
}
