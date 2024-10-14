package com.canvas.persistence.jpa.diary;

import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.domain.diary.vo.DiaryContent;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import com.canvas.persistence.jpa.diary.entity.ImageEntity;
import com.canvas.persistence.jpa.diary.entity.LikeEntity;

import java.util.List;

public class DiaryMapper {
    public static DiaryEntity toEntity(Diary diary) {
        return new DiaryEntity(
                diary.getId(),
                diary.getDiaryContent().getContent(),
                diary.getDiaryContent().getEmotion().name(),
                diary.getIsPublic(),
                diary.getWriterId()
        );
    }

    public static Diary toDomain(DiaryEntity diaryEntity, List<ImageEntity> imageEntities, List<LikeEntity> likeEntities) {
        return Diary.withId(
                diaryEntity.getId(),
                diaryEntity.getWriterId(),
                new DiaryContent(
                        diaryEntity.getContent(),
                        imageEntities.stream()
                                .map(ImageMapper::toDomain)
                                .toList(),
                        Emotion.parse(diaryEntity.getEmotion())
                ),
                diaryEntity.getCreatedAt(),
                likeEntities.stream()
                        .map(LikeMapper::toDomain)
                        .toList(),
                diaryEntity.getIsPublic()
        );
    }
}