package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import com.canvas.persistence.jpa.diary.entity.ImageEntity;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.canvas.persistence.jpa.fixture.ImageEntityFixture.PUBLIC_MY_DIARY_IMAGE1;
import static com.canvas.persistence.jpa.fixture.UserEntityFixture.MYSELF;
import static com.canvas.persistence.jpa.fixture.UserEntityFixture.OTHER1;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImageJpaRepositoryTest {

    @Autowired
    private ImageJpaRepository imageJpaRepository;
    @Autowired
    private DiaryJpaRepository diaryJpaRepository;
    @Autowired
    private UserJpaRepository userJpaRepository;

    @BeforeAll
    void setUp() {
        List<UserEntity> userEntities = Arrays.stream(UserEntityFixture.values())
                .map(UserEntityFixture::getUserEntity)
                .toList();

        List<DiaryEntity> diaryEntities = Arrays.stream(DiaryEntityFixture.values())
                .map(DiaryEntityFixture::getDiaryEntity)
                .toList();

        userJpaRepository.saveAll(userEntities);
        diaryJpaRepository.saveAll(diaryEntities);
    }

    @Test
    @DisplayName("ID, 작성자 ID 조회 성공")
    void existsByIdAndWriterIdSuccessTest() {
        // given
        ImageEntity publicMyDiaryImage1 = PUBLIC_MY_DIARY_IMAGE1.getImageEntity();
        UserEntity myself = MYSELF.getUserEntity();

        // when
        Optional<ImageEntity> imageEntity = imageJpaRepository.findByIdAndWriterId(
                publicMyDiaryImage1.getId(),
                myself.getId()
        );

        // then
        assertThat(imageEntity).isPresent();
    }

    @Test
    @DisplayName("ID, 작성자 ID 조회 실패")
    void existsByIdAndWriterIdFailureTest() {
        // given
        ImageEntity publicMyDiaryImage1 = PUBLIC_MY_DIARY_IMAGE1.getImageEntity();
        UserEntity other1 = OTHER1.getUserEntity();

        // when
        Optional<ImageEntity> imageEntity = imageJpaRepository.findByIdAndWriterId(
                publicMyDiaryImage1.getId(),
                other1.getId()
        );

        // then
        assertThat(imageEntity).isEmpty();
    }

    @Test
    @DisplayName("일기 내 모든 이미지 조회 성공")
    void findAllInDiaryByIdAndWriterIdSuccessTest() {
        // given
        ImageEntity publicMyDiaryImage1 = PUBLIC_MY_DIARY_IMAGE1.getImageEntity();
        DiaryEntity publicMyDiary = DiaryEntityFixture.PUBLIC_MY_DIARY.getDiaryEntity();
        UserEntity myself = MYSELF.getUserEntity();

        // when
        List<UUID> ImageIds = publicMyDiary.getImageEntities()
                .stream()
                .map(ImageEntity::getId)
                .toList();
        List<ImageEntity> publicMyDiaryImages = imageJpaRepository.findAllInDiaryByIdAndWriterId(publicMyDiaryImage1.getId(), myself.getId());

        ImageIds.forEach(id -> log.info("id={}", id));
        publicMyDiaryImages
                .forEach(imageEntity -> log.info("gotId={}", imageEntity.getId()));

        // then
        assertThat(ImageIds)
                .isSubsetOf(publicMyDiaryImages.stream()
                        .map(ImageEntity::getId)
                        .toList());
    }

    @Test
    @DisplayName("일기 내 모든 이미지 조회 실패")
    void findAllInDiaryByIdAndWriterIdFailureTest() {
        // given
        ImageEntity publicMyDiaryImage1 = PUBLIC_MY_DIARY_IMAGE1.getImageEntity();
        UserEntity other1 = OTHER1.getUserEntity();

        // when
        List<ImageEntity> publicMyDiaryImages = imageJpaRepository.findAllInDiaryByIdAndWriterId(publicMyDiaryImage1.getId(), other1.getId());

        publicMyDiaryImages
                .forEach(imageEntity -> log.info("gotId={}", imageEntity.getId()));

        // then
        assertThat(publicMyDiaryImages)
                .isEmpty();
    }

}