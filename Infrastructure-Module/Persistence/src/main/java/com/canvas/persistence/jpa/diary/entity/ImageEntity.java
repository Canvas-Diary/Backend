package com.canvas.persistence.jpa.diary.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean isMain;
    private String s3Uri;

    @Column(name = "diary_id", nullable = false)
    private Long diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", insertable = false, updatable = false)
    private DiaryEntity diary;

    public ImageEntity(Long id, Boolean isMain, String s3Uri, Long diaryId) {
        this.id = id;
        this.isMain = isMain;
        this.s3Uri = s3Uri;
        this.diaryId = diaryId;
    }

}
