package com.canvas.persistence.jpa.diary.entity;

import com.canvas.persistence.jpa.common.BaseEntity;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiaryEntity extends BaseEntity {

    @Id
    private UUID id;

    @Column(length = 1_500)
    private String content;
    private String emotion;
    private Boolean isPublic;

    @Column(name = "writer_id", nullable = false)
    private UUID writerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", insertable = false, updatable = false)
    private UserEntity writer;

    @OneToMany(mappedBy = "diaryEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> imageEntities = new ArrayList<>();
    @OneToMany(mappedBy = "diaryEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeEntity> likeEntities = new ArrayList<>();

    public DiaryEntity(UUID id, String content, String emotion, Boolean isPublic, UUID writerId) {
        this.id = id;
        this.content = content;
        this.emotion = emotion;
        this.isPublic = isPublic;
        this.writerId = writerId;
    }

    public void addImageEntity(ImageEntity imageEntity) {
        imageEntities.add(imageEntity);
    }

    public void addLikeEntity(LikeEntity likeEntity) {
        likeEntities.add(likeEntity);
    }

}
