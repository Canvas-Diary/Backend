package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.vo.DiaryContent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class Diary {
    private DomainId id;
    private DomainId writerId;
    private DiaryContent diaryContent;
    private LocalDateTime dateTime;
    private List<Like> likes;
    private Boolean isPublic;

    public static Diary withoutId(DomainId userId, DiaryContent diaryContent, LocalDateTime datetime, List<Like> likes, Boolean isPublic) {
        return new Diary(DomainId.generate(), userId, diaryContent, datetime, likes, isPublic);
    }

    public static Diary withId(DomainId domainId, DomainId userId, DiaryContent diaryContent, LocalDateTime datetime, List<Like> likes, Boolean isPublic) {
        return new Diary(domainId, userId, diaryContent, datetime, likes, isPublic);
    }
}
