package com.canvas.domain.diary.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.canvas.domain.fixture.ImageFixture.PUBLIC_MY_DIARY_IMAGE1;
import static com.canvas.domain.fixture.ImageFixture.PUBLIC_MY_DIARY_IMAGE2;
import static org.assertj.core.api.Assertions.assertThat;

class ImageTest {
    @Test
    @DisplayName("메인 이미지 업데이트")
    void updateMainTest() {
        // given
        Image image1 = PUBLIC_MY_DIARY_IMAGE1.getImage();
        Image image2 = PUBLIC_MY_DIARY_IMAGE2.getImage();

        // when
        image1.updateMain(false);
        image2.updateMain(true);

        // then
        assertThat(image1.isMain()).isFalse();
        assertThat(image2.isMain()).isTrue();
    }

    @Test
    @DisplayName("메인 이미지 여부 반환")
    void isMainTest() {
        // given
        Image image1 = PUBLIC_MY_DIARY_IMAGE1.getImage();
        Image image2 = PUBLIC_MY_DIARY_IMAGE2.getImage();

        // when
        Boolean isMain1 = image1.isMain();
        Boolean isMain2 = image2.isMain();

        // then
        assertThat(isMain1).isTrue();
        assertThat(isMain2).isFalse();
    }
}