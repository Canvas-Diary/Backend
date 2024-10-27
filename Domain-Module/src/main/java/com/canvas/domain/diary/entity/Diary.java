package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.vo.DiaryContent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Diary {
    private DomainId id;
    private DomainId writerId;
    private DiaryContent diaryContent;
    private LocalDateTime dateTime;
    private Boolean isPublic;
    private List<Like> likes;

    public static Diary create(DomainId id, DomainId writerId, DiaryContent diaryContent, LocalDateTime dateTime, Boolean isPublic, List<Like> likes) {
        return new Diary(id, writerId, diaryContent, dateTime, isPublic, likes);
    }

    public static Diary create(DomainId id, DomainId writerId, DiaryContent diaryContent, LocalDateTime dateTime, Boolean isPublic) {
        return new Diary(id, writerId, diaryContent, dateTime, isPublic, new ArrayList<>());
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public void updateDiaryContent(DiaryContent diaryContent) {
        this.diaryContent = diaryContent;
    }

    public void updatePublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getMainImageOrDefault() {
        return this.diaryContent.getMainImageOrDefault();
    }
}
