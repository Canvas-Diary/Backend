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
                image.getImageUrl(),
                image.getDiaryId().value()
        );
    }

    public static List<ImageEntity> toEntities(Diary diary) {
        return diary.getImages().stream()
                .map(ImageMapper::toEntity)
                .toList();
    }

    public static Image toDomain(ImageEntity imageEntity) {
        return Image.create(
                DomainId.from(imageEntity.getId()),
                DomainId.from(imageEntity.getDiaryId()),
                imageEntity.getIsMain(),
                imageEntity.getImageUrl()
        );
    }

}
