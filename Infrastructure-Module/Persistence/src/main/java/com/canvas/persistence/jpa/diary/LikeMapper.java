package com.canvas.persistence.jpa.diary;

import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.entity.Like;
import com.canvas.persistence.jpa.diary.entity.LikeEntity;

import java.util.List;

public class LikeMapper {
    public static LikeEntity toEntity(Like like) {
        return new LikeEntity(
                like.getId(),
                like.getUserId(),
                like.getDiaryId()
        );
    }

    public static List<LikeEntity> toEntities(Diary diary) {
        return diary.getLikes().stream()
                .map(LikeMapper::toEntity)
                .toList();
    }

    public static Like toDomain(LikeEntity likeEntity) {
        return Like.withId(
                likeEntity.getId(),
                likeEntity.getUserId(),
                likeEntity.getDiaryId()
        );
    }
}
