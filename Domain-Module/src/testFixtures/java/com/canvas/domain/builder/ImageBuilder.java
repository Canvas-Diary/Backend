package com.canvas.domain.builder;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Image;

public class ImageBuilder {
    private DomainId id;
    private DomainId diaryId;
    private Boolean isMain;
    private String imageUrl;

    public ImageBuilder id(DomainId id) {
        this.id = id;
        return this;
    }

    public ImageBuilder diaryId(DomainId diaryId) {
        this.diaryId = diaryId;
        return this;
    }

    public ImageBuilder isMain(Boolean isMain) {
        this.isMain = isMain;
        return this;
    }

    public ImageBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Image build() {
        return Image.create(id, diaryId, isMain, imageUrl);
    }
}
