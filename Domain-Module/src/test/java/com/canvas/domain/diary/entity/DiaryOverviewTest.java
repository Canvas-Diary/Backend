package com.canvas.domain.diary.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.canvas.domain.fixture.DiaryFixture.NO_MAIN_IMAGE_DIARY;
import static com.canvas.domain.fixture.DiaryFixture.PUBLIC_OTHER_DIARY;
import static com.canvas.domain.fixture.ImageFixture.PUBLIC_OTHER_DIARY_IMAGE2;
import static org.assertj.core.api.Assertions.assertThat;

class DiaryOverviewTest {
    
    @Test
    @DisplayName("메인 이미지 URL 반환")
    void getMainImageTest() {
        // given
        DiaryOverview diary = PUBLIC_OTHER_DIARY.getDiaryOverview();
        Image mainImage = PUBLIC_OTHER_DIARY_IMAGE2.getImage();

        // when
        String imageUrl = diary.getMainImageOrDefault();

        // then
        assertThat(imageUrl).isEqualTo(mainImage.getImageUrl());
    }

    @Test
    @DisplayName("기본 이미지 URL 반환")
    void getDefaultTest() {
        // given
        DiaryOverview diary = NO_MAIN_IMAGE_DIARY.getDiaryOverview();
        String defaultImageUrl = Image.DEFAULT_IMAGE_URL;

        // when
        String imageUrl = diary.getMainImageOrDefault();

        // then
        assertThat(imageUrl).isEqualTo(defaultImageUrl);
    }

}