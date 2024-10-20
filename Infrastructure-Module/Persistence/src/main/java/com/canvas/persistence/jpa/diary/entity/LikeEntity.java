package com.canvas.persistence.jpa.diary.entity;

import com.canvas.persistence.jpa.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LikeEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "diary_id", nullable = false)
    private UUID diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", insertable = false, updatable = false)
    private DiaryEntity diaryEntity;

    public LikeEntity(UUID id, UUID userId, UUID diaryId) {
        this.id = id;
        this.userId = userId;
        this.diaryId = diaryId;
    }

}
