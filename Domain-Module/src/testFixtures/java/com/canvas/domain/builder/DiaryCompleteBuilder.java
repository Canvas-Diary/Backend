package com.canvas.domain.builder;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.entity.Like;
import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DiaryCompleteBuilder {
    private DomainId id;
    private DomainId writerId;
    private String content;
    private Emotion emotion;
    private LocalDate date;
    private LocalDateTime createdAt;
    private Boolean isPublic;
    private List<Image> images;
    private List<Like> likes;

    public DiaryCompleteBuilder id(DomainId id) {
        this.id = id;
        return this;
    }

    public DiaryCompleteBuilder writerId(DomainId writerId) {
        this.writerId = writerId;
        return this;
    }

    public DiaryCompleteBuilder content(String content) {
        this.content = content;
        return this;
    }

    public DiaryCompleteBuilder emotion(Emotion emotion) {
        this.emotion = emotion;
        return this;
    }

    public DiaryCompleteBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public DiaryCompleteBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public DiaryCompleteBuilder isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public DiaryCompleteBuilder images(List<Image> images) {
        this.images = images;
        return this;
    }

    public DiaryCompleteBuilder likes(List<Like> likes) {
        this.likes = likes;
        return this;
    }

    public DiaryComplete build() {
        return DiaryComplete.create(id, writerId, content, emotion, date, createdAt, isPublic, images, likes);
    }

}
