package com.canvas.persistence.jpa.builder;

import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import com.canvas.persistence.jpa.diary.entity.LikeEntity;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

public class LikeEntityBuilder {

    private UUID id;
    private UUID userId;
    private UUID diaryId;
    private UserEntity userEntity;
    private DiaryEntity diaryEntity;

    public LikeEntityBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public LikeEntityBuilder userId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public LikeEntityBuilder diaryId(UUID diaryId) {
        this.diaryId = diaryId;
        return this;
    }

    public LikeEntityBuilder userEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public LikeEntityBuilder diaryEntity(DiaryEntity diaryEntity) {
        this.diaryEntity = diaryEntity;
        return this;
    }

    public LikeEntity build() {
        LikeEntity likeEntity = new LikeEntity(id, userId, diaryId);

        ReflectionTestUtils.setField(likeEntity, "userEntity", userEntity);
        ReflectionTestUtils.setField(likeEntity, "diaryEntity", diaryEntity);

        return likeEntity;
    }

}
