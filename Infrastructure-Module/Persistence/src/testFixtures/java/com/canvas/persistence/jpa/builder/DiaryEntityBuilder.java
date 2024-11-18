package com.canvas.persistence.jpa.builder;

import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import com.canvas.persistence.jpa.diary.entity.ImageEntity;
import com.canvas.persistence.jpa.diary.entity.LikeEntity;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class DiaryEntityBuilder {

    private UUID id;
    private String content;
    private String weightedContents;
    private String emotion;
    private Boolean isPublic;
    private LocalDate date;
    private UUID writerId;
    private UserEntity writer;
    private List<ImageEntity> imageEntities;
    private List<LikeEntity> likeEntities;

    public DiaryEntityBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public DiaryEntityBuilder content(String content) {
        this.content = content;
        return this;
    }

    public DiaryEntityBuilder weightedContents(String weightedContents) {
        this.weightedContents = weightedContents;
        return this;
    }

    public DiaryEntityBuilder emotion(String emotion) {
        this.emotion = emotion;
        return this;
    }

    public DiaryEntityBuilder isPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    public DiaryEntityBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public DiaryEntityBuilder writerId(UUID writerId) {
        this.writerId = writerId;
        return this;
    }

    public DiaryEntityBuilder writer(UserEntity writer) {
        this.writer = writer;
        return this;
    }

    public DiaryEntityBuilder imageEntities(List<ImageEntity> imageEntities) {
        this.imageEntities = imageEntities;
        return this;
    }

    public DiaryEntityBuilder likeEntities(List<LikeEntity> likeEntities) {
        this.likeEntities = likeEntities;
        return this;
    }

    public DiaryEntity build() {
        DiaryEntity diary = new DiaryEntity(
                this.id,
                this.content,
                this.weightedContents,
                this.emotion,
                this.isPublic,
                this.date,
                this.writerId
        );

        ReflectionTestUtils.setField(diary, "writer", this.writer);
        ReflectionTestUtils.setField(diary, "imageEntities", this.imageEntities);
        ReflectionTestUtils.setField(diary, "likeEntities", this.likeEntities);

        return diary;
    }

}
