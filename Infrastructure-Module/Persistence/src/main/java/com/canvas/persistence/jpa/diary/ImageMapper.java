package com.canvas.persistence.jpa.diary;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.entity.Image;
import com.canvas.persistence.jpa.diary.entity.ImageEntity;

import java.util.List;

public class ImageMapper {
    public static ImageEntity toEntity(Image image) {
        return new ImageEntity(
                image.getId().value(),
                image.getIsMain(),
                image.getS3Uri(),
                image.getDiaryId().value()
        );
    }

    public static List<ImageEntity> toEntities(Diary diary) {
        return diary.getDiaryContent().getImages().stream()
                .map(ImageMapper::toEntity)
                .toList();
    }

    public static Image toDomain(ImageEntity imageEntity) {
        return new Image(
                new DomainId(imageEntity.getId()),
                new DomainId(imageEntity.getDiaryId()),
                imageEntity.getIsMain(),
                imageEntity.getS3Uri()
        );
    }

}
