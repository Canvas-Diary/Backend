package com.canvas.persistence.jpa.diary;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.entity.Like;
import com.canvas.persistence.jpa.diary.entity.LikeEntity;

import java.util.List;

public class LikeMapper {
    public static LikeEntity toEntity(Like like) {
        return new LikeEntity(
                like.getId().value(),
                like.getUserId().value(),
                like.getDiaryId().value()
        );
    }

    public static List<LikeEntity> toEntities(Diary diary) {
        return diary.getLikes().stream()
                .map(LikeMapper::toEntity)
                .toList();
    }

    public static Like toDomain(LikeEntity likeEntity) {
        return Like.withId(
                new DomainId(likeEntity.getId()),
                new DomainId(likeEntity.getUserId()),
                new DomainId(likeEntity.getDiaryId())
        );
    }
}
