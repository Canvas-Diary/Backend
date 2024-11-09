package com.canvas.domain.diary.entity;

import com.canvas.domain.diary.enums.Emotion;
import com.canvas.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.canvas.domain.diary.enums.Emotion.DISGUST;
import static com.canvas.domain.fixture.DiaryFixture.PUBLIC_MY_DIARY;
import static com.canvas.domain.fixture.DiaryFixture.PUBLIC_OTHER_DIARY;
import static com.canvas.domain.fixture.UserFixture.MYSELF;
import static com.canvas.domain.fixture.UserFixture.OTHER1;
import static org.assertj.core.api.Assertions.assertThat;

class DiaryCompleteTest {

    @Test
    @DisplayName("일기 내용 업데이트")
    void updateDiaryContentTest() {
        // given
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();

        String newContent = "내용1 수정";
        Emotion newEmotion = DISGUST;

        // when
        diary.updateDiaryContent(newContent, newEmotion);

        // then
        assertThat(diary.getContent()).isEqualTo(newContent);
        assertThat(diary.getEmotion()).isEqualTo(newEmotion);
    }

    @Test
    @DisplayName("일기 공개 범위 업데이트")
    void updatePublicTest() {
        // given
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        boolean newPublic = false;

        // when
        diary.updatePublic(newPublic);

        // then
        assertThat(diary.getIsPublic()).isEqualTo(newPublic);
    }

    @Test
    @DisplayName("좋아요 개수 반환")
    void getLikeCountTest() {
        // given
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();

        // when
        int likeCount = diary.getLikeCount();

        // then
        assertThat(likeCount).isEqualTo(3);
    }

    @Test
    @DisplayName("좋아요 여부 반환")
    void isLikedTest() {
        // given
        DiaryComplete diary = PUBLIC_OTHER_DIARY.getDiaryComplete();
        User myself = MYSELF.getUser();
        User other1 = OTHER1.getUser();

        // when
        boolean isLiked = diary.isLiked(myself.getId());
        boolean isNotLiked = diary.isLiked(other1.getId());

        // then
        assertThat(isLiked).isTrue();
        assertThat(isNotLiked).isFalse();
    }

    @Test
    @DisplayName("글쓴이 여부 반환")
    void isWriterTest() {
        // given
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        User myself = MYSELF.getUser();
        User other1 = OTHER1.getUser();

        // when
        boolean isWriter = diary.isWriter(myself.getId());
        boolean isNotWriter = diary.isWriter(other1.getId());

        // then
        assertThat(isWriter).isTrue();
        assertThat(isNotWriter).isFalse();
    }

}