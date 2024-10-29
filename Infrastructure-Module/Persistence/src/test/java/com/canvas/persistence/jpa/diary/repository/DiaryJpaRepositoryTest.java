package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import com.canvas.persistence.jpa.fixture.DiaryEntityFixture;
import com.canvas.persistence.jpa.fixture.UserEntityFixture;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import com.canvas.persistence.jpa.user.repository.UserJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static com.canvas.persistence.jpa.fixture.DiaryEntityFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DiaryJpaRepositoryTest {

    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @BeforeAll
    void setUp() {
        List<UserEntity> users = Arrays.stream(UserEntityFixture.values())
                .map(UserEntityFixture::getUserEntity)
                .toList();

        List<DiaryEntity> diaries = Arrays.stream(values())
                .map(DiaryEntityFixture::getDiaryEntity)
                .toList();

        userJpaRepository.saveAll(users);
        diaryJpaRepository.saveAll(diaries);
    }

    @Test
    @DisplayName("ID, public 조회 성공")
    void findPublicByIdSuccessTest() {
        // given
        DiaryEntity publicMyDiary = PUBLIC_MY_DIARY.getDiaryEntity();

        // when
        // then
        assertThat(diaryJpaRepository.findPublicById(publicMyDiary.getId()))
                .isPresent()
                .get()
                .extracting(DiaryEntity::getId)
                .isEqualTo(publicMyDiary.getId());
    }

    @Test
    @DisplayName("ID, public 조회 실패")
    void findPublicByIdFailureTest() {
        // given
        DiaryEntity privateOtherDiary = PRIVATE_OTHER_DIARY.getDiaryEntity();

        // when
        // then
        assertThat(diaryJpaRepository.findPublicById(privateOtherDiary.getId()))
                .isNotPresent();
    }

    @Test
    @DisplayName("ID, 작성자 ID 조회 성공")
    void findByIdAndWriterIdAccessTest() {
        // given
        DiaryEntity publicMyDiary = PUBLIC_MY_DIARY.getDiaryEntity();

        // when
        // then
        assertThat(diaryJpaRepository.findByIdAndWriterId(publicMyDiary.getId(), publicMyDiary.getWriterId()))
                .isPresent()
                .get()
                .extracting(DiaryEntity::getId)
                .isEqualTo(publicMyDiary.getId());
    }

    @Test
    @DisplayName("ID, 작성자 ID 조회 실패")
    void findByIdAndWriterIdFailureTest() {
        // given
        DiaryEntity publicMyDiary = PUBLIC_MY_DIARY.getDiaryEntity();
        DiaryEntity publicOtherDiary = PUBLIC_OTHER_DIARY.getDiaryEntity();

        // when
        // then
        assertThat(diaryJpaRepository.findByIdAndWriterId(publicMyDiary.getId(), publicOtherDiary.getWriterId()))
                .isNotPresent();
    }

    @Test
    @DisplayName("작성자 ID, 기간 조회 성공")
    void findByWriterIdAndCreatedAtBetweenSuccessTest() {
        // given
        DiaryEntity publicMyDiary = PUBLIC_MY_DIARY.getDiaryEntity();

        // when
        // then
        assertThat(
                diaryJpaRepository.findByWriterIdAndCreatedAtBetween(
                        publicMyDiary.getWriterId(),
                        LocalDate.now().atStartOfDay(),
                        LocalDate.now().atTime(LocalTime.MAX)))
                .extracting(DiaryEntity::getId)
                .contains(publicMyDiary.getId());
    }

    @Test
    @DisplayName("작성자 ID, 기간 조회 실패")
    void findByWriterIdAndCreatedAtBetweenFailureTest() {
        // given
        DiaryEntity publicMyDiary = PUBLIC_MY_DIARY.getDiaryEntity();

        // when
        // then
        assertThat(
                diaryJpaRepository.findByWriterIdAndCreatedAtBetween(
                        publicMyDiary.getWriterId(),
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(2)))
                .isEmpty();
    }
}