package com.canvas.persistence.jpa.builder;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import com.canvas.persistence.jpa.diary.entity.ImageEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

public class ImageEntityBuilder {
    private UUID id = DomainId.generate().value();
    private Boolean isMain = false;
    private String imageUrl = "url";
    private UUID diaryId = DomainId.generate().value();
    private DiaryEntity diaryEntity;

    public ImageEntityBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public ImageEntityBuilder isMain(Boolean isMain) {
        this.isMain = isMain;
        return this;
    }

    public ImageEntityBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public ImageEntityBuilder diaryId(UUID diaryId) {
        this.diaryId = diaryId;
        return this;
    }
    
    public ImageEntityBuilder diaryEntity(DiaryEntity diaryEntity) {
        this.diaryEntity = diaryEntity;
        return this;
    }

    public ImageEntity build() {
        ImageEntity image = new ImageEntity(id, isMain, imageUrl, diaryId);
        
        ReflectionTestUtils.setField(image, "diaryEntity", this.diaryEntity);
        
        return image;
    }
}
