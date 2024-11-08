package com.canvas.domain.fixture;

import com.canvas.domain.builder.ImageBuilder;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Image;
import lombok.Getter;

import static com.canvas.domain.fixture.DiaryFixture.*;

public enum ImageFixture {
    PUBLIC_MY_DIARY_IMAGE1(true, "url1", PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_IMAGE2(false, "url2", PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_IMAGE3(false, "url3", PUBLIC_MY_DIARY),
    PUBLIC_OTHER_DIARY_IMAGE1(false, "url4", PUBLIC_OTHER_DIARY),
    PUBLIC_OTHER_DIARY_IMAGE2(true, "url5", PUBLIC_OTHER_DIARY),
    PRIVATE_OTHER_DIARY_IMAGE1(true, "url6", PRIVATE_OTHER_DIARY);

    private final DomainId id;
    private final Boolean isMain;
    private final String imageUrl;
    @Getter
    private final DiaryFixture diaryFixture;

    ImageFixture(
            Boolean isMain,
            String imageUrl,
            DiaryFixture diaryFixture
    ) {
        this.id = DomainId.generate();
        this.isMain = isMain;
        this.imageUrl = imageUrl;
        this.diaryFixture = diaryFixture;
    }

    public Image getImage() {
        return new ImageBuilder()
                .id(id)
                .isMain(isMain)
                .imageUrl(imageUrl)
                .diaryId(diaryFixture.getId())
                .build();
    }
}
