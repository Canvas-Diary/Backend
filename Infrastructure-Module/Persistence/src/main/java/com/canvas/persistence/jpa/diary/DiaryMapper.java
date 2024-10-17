package com.canvas.persistence.jpa.diary;

import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.domain.diary.vo.DiaryContent;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;

public class DiaryMapper {
    public static DiaryEntity toEntity(Diary diary) {
        DiaryEntity diaryEntity = new DiaryEntity(
                diary.getId(),
                diary.getDiaryContent().getContent(),
                diary.getDiaryContent().getEmotion().name(),
                diary.getIsPublic(),
                diary.getWriterId()
        );

        diaryEntity.getImageEntities().addAll(ImageMapper.toEntities(diary));
        diaryEntity.getLikeEntities().addAll(LikeMapper.toEntities(diary));

        return diaryEntity;
    }

    public static Diary toDomain(DiaryEntity diaryEntity) {
        return Diary.withId(
                diaryEntity.getId(),
                diaryEntity.getWriterId(),
                new DiaryContent(
                        diaryEntity.getContent(),
                        diaryEntity.getImageEntities().stream()
                                .map(ImageMapper::toDomain)
                                .toList(),
                        Emotion.parse(diaryEntity.getEmotion())
                ),
                diaryEntity.getCreatedAt(),
                diaryEntity.getLikeEntities().stream()
                        .map(LikeMapper::toDomain)
                        .toList(),
                diaryEntity.getIsPublic()
        );
    }
}
