package com.canvas.persistence.jpa.diary.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ImageEntity {

    @Id
    private UUID id;

    private Boolean isMain;
    private String imageUrl;

    @Column(name = "diary_id", nullable = false)
    private UUID diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", insertable = false, updatable = false)
    private DiaryEntity diaryEntity;

    public ImageEntity(UUID id, Boolean isMain, String imageUrl, UUID diaryId) {
        this.id = id;
        this.isMain = isMain;
        this.imageUrl = imageUrl;
        this.diaryId = diaryId;
    }

}
