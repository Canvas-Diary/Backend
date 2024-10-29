package com.canvas.persistence.jpa.fixture;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.builder.ImageEntityBuilder;
import com.canvas.persistence.jpa.diary.entity.ImageEntity;
import lombok.Getter;

import java.util.UUID;

import static com.canvas.persistence.jpa.fixture.DiaryFixture.*;

public enum ImageFixture {
    PUBLIC_MY_DIARY_IMAGE1(true, "url1", PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_IMAGE2(false, "url2", PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_IMAGE3(false, "url3", PUBLIC_MY_DIARY),
    PUBLIC_OTHER_DIARY_IMAGE1(false, "url4", PUBLIC_OTHER_DIARY),
    PUBLIC_OTHER_DIARY_IMAGE2(true, "url5", PUBLIC_OTHER_DIARY),
    PRIVATE_OTHER_DIARY_IMAGE1(true, "url6", PRIVATE_OTHER_DIARY);

    private final UUID id;
    private final Boolean isMain;
    private final String imageUrl;
    @Getter
    private final DiaryFixture diaryFixture;

    ImageFixture(Boolean isMain, String imageUrl, DiaryFixture diaryFixture) {
        this.id = DomainId.generate().value();
        this.isMain = isMain;
        this.imageUrl = imageUrl;
        this.diaryFixture = diaryFixture;
    }

    public ImageEntity getImageEntity() {
        return new ImageEntityBuilder()
                .id(id)
                .isMain(isMain)
                .imageUrl(imageUrl)
                .diaryId(diaryFixture.getId())
                .build();
    }

}
