package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.vo.DiaryContent;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Diary {
    private DomainId id;
    private DomainId writerId;
    private DiaryContent diaryContent;
    private LocalDateTime dateTime;
    private Boolean isPublic;
    private List<Like> likes = new ArrayList<>();

    public Diary(DomainId id, DomainId writerId, DiaryContent diaryContent, LocalDateTime dateTime, Boolean isPublic, List<Like> likes) {
        this.id = id;
        this.writerId = writerId;
        this.diaryContent = diaryContent;
        this.dateTime = dateTime;
        this.isPublic = isPublic;
        this.likes.addAll(likes);
    }

    public Diary(DomainId id, DomainId writerId, DiaryContent diaryContent, LocalDateTime dateTime, Boolean isPublic) {
        this.id = id;
        this.writerId = writerId;
        this.diaryContent = diaryContent;
        this.dateTime = dateTime;
        this.isPublic = isPublic;
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
}
