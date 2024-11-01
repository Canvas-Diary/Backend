package com.canvas.persistence.jpa.diary;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryBasic;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.DiaryOverview;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;

public class DiaryMapper {
    public static DiaryEntity toEntity(DiaryComplete diary) {
        DiaryEntity diaryEntity = new DiaryEntity(
                diary.getId().value(),
                diary.getContent(),
                diary.getEmotion().name(),
                diary.getIsPublic(),
                diary.getDateTime(),
                diary.getWriterId().value()
        );

        ImageMapper.toEntities(diary).forEach(diaryEntity::addImageEntity);
        LikeMapper.toEntities(diary).forEach(diaryEntity::addLikeEntity);

        return diaryEntity;
    }

    public static DiaryBasic toBasicDomain(DiaryEntity diaryEntity) {
        return DiaryBasic.create(
                DomainId.from(diaryEntity.getId()),
                DomainId.from(diaryEntity.getWriterId()),
                diaryEntity.getContent(),
                Emotion.parse(diaryEntity.getEmotion()),
                diaryEntity.getDateTime(),
                diaryEntity.getCreatedAt(),
                diaryEntity.getIsPublic()
        );
    }

    public static DiaryOverview toOverviewDomain(DiaryEntity diaryEntity) {
        return DiaryOverview.create(
                DomainId.from(diaryEntity.getId()),
                DomainId.from(diaryEntity.getWriterId()),
                diaryEntity.getContent(),
                Emotion.parse(diaryEntity.getEmotion()),
                diaryEntity.getDateTime(),
                diaryEntity.getCreatedAt(),
                diaryEntity.getIsPublic(),
                diaryEntity.getImageEntities().stream()
                        .map(ImageMapper::toDomain)
                        .toList()
        );
    }

    public static DiaryComplete toCompleteDomain(DiaryEntity diaryEntity) {
        return DiaryComplete.create(
                DomainId.from(diaryEntity.getId()),
                DomainId.from(diaryEntity.getWriterId()),
                diaryEntity.getContent(),
                Emotion.parse(diaryEntity.getEmotion()),
                diaryEntity.getDateTime(),
                diaryEntity.getCreatedAt(),
                diaryEntity.getIsPublic(),
                diaryEntity.getImageEntities().stream()
                        .map(ImageMapper::toDomain)
                        .toList(),
                diaryEntity.getLikeEntities().stream()
                        .map(LikeMapper::toDomain)
                        .toList()
        );
    }
}
