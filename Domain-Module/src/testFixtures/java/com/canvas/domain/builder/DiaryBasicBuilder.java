package com.canvas.domain.builder;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryBasic;
import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DiaryBasicBuilder {
    private DomainId id;
    private DomainId writerId;
    private String content;
    private Emotion emotion;
    private LocalDate date;
    private LocalDateTime createdAt;
    private Boolean isPublic;

    public DiaryBasicBuilder id(DomainId id) {
        this.id = id;
        return this;
    }

    public DiaryBasicBuilder writerId(DomainId writerId) {
        this.writerId = writerId;
        return this;
    }

    public DiaryBasicBuilder content(String content) {
        this.content = content;
        return this;
    }

    public DiaryBasicBuilder emotion(Emotion emotion) {
        this.emotion = emotion;
        return this;
    }

    public DiaryBasicBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public DiaryBasicBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public DiaryBasicBuilder isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public DiaryBasic build() {
        return DiaryBasic.create(id, writerId, content, emotion, date, createdAt, isPublic);
    }
}
