package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Image {
    private DomainId id;
    private DomainId diaryId;
    private Boolean isMain;
    private String imageUrl;

    public static final String DEFAULT_IMAGE_URL = "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/sample.jpg";

    public static Image create(DomainId id, DomainId diaryId, Boolean isMain, String imageUrl) {
        return new Image(id, diaryId, isMain, imageUrl);
    }

    public void updateMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public Boolean isMain(){
        return this.isMain;
    }
}
