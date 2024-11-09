package com.canvas.domain.builder;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryOverview;
import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DiaryOverviewBuilder {
    private DomainId id;
    private DomainId writerId;
    private String content;
    private Emotion emotion;
    private LocalDate date;
    private LocalDateTime createdAt;
    private Boolean isPublic;
    private List<Image> images;

    public DiaryOverviewBuilder id(DomainId id) {
        this.id = id;
        return this;
    }

    public DiaryOverviewBuilder writerId(DomainId writerId) {
        this.writerId = writerId;
        return this;
    }

    public DiaryOverviewBuilder content(String content) {
        this.content = content;
        return this;
    }

    public DiaryOverviewBuilder emotion(Emotion emotion) {
        this.emotion = emotion;
        return this;
    }

    public DiaryOverviewBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public DiaryOverviewBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public DiaryOverviewBuilder isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public DiaryOverviewBuilder images(List<Image> images) {
        this.images = images;
        return this;
    }

    public DiaryOverview build() {
        return DiaryOverview.create(id, writerId, content, emotion, date, createdAt, isPublic, images);
    }
}
