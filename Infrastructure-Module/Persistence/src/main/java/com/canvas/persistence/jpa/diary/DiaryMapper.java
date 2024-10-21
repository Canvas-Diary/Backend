package com.canvas.persistence.jpa.diary;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.domain.diary.vo.DiaryContent;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;

public class DiaryMapper {
    public static DiaryEntity toEntity(Diary diary) {
        DiaryEntity diaryEntity = new DiaryEntity(
                diary.getId().value(),
                diary.getDiaryContent().getContent(),
                diary.getDiaryContent().getEmotion().name(),
                diary.getIsPublic(),
                diary.getWriterId().value()
        );

        ImageMapper.toEntities(diary).forEach(diaryEntity::addImageEntity);
        LikeMapper.toEntities(diary).forEach(diaryEntity::addLikeEntity);

        return diaryEntity;
    }

    public static Diary toDomain(DiaryEntity diaryEntity) {
        return new Diary(
                new DomainId(diaryEntity.getId()),
                new DomainId(diaryEntity.getWriterId()),
                new DiaryContent(
                        diaryEntity.getContent(),
                        Emotion.parse(diaryEntity.getEmotion()),
                        diaryEntity.getImageEntities().stream()
                                .map(ImageMapper::toDomain)
                                .toList()
                ),
                diaryEntity.getCreatedAt(),
                diaryEntity.getIsPublic(),
                diaryEntity.getLikeEntities().stream()
                        .map(LikeMapper::toDomain)
                        .toList()
        );
    }
}
