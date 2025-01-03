package com.canvas.domain.fixture;

import com.canvas.domain.builder.DiaryBasicBuilder;
import com.canvas.domain.builder.DiaryCompleteBuilder;
import com.canvas.domain.builder.DiaryOverviewBuilder;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryBasic;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.DiaryOverview;
import com.canvas.domain.diary.enums.Emotion;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.canvas.domain.diary.enums.Emotion.*;
import static com.canvas.domain.fixture.UserFixture.*;

public enum DiaryFixture {
    PUBLIC_MY_DIARY(
            "내용1",
            List.of("강조내용1"),
            JOY,
            LocalDate.of(2024, 10, 15),
            LocalDateTime.now(),
            true,
            MYSELF
    ),
    PUBLIC_OTHER_DIARY(
            "내용2",
            List.of(),
            ANGER,
            LocalDate.of(2024, 10, 17),
            LocalDateTime.now(),
            true,
            OTHER1
    ),
    PRIVATE_OTHER_DIARY(
            "내용3",
            List.of("강조내용3"),
            SADNESS,
            LocalDate.of(2024, 10, 31),
            LocalDateTime.now(),
            false,
            OTHER2
    ),
    NO_MAIN_IMAGE_DIARY(
            "내용4",
            List.of(),
            FEAR,
            LocalDate.of(2024, 11, 1),
            LocalDateTime.now(),
            true,
            MYSELF
    );

    @Getter
    private final DomainId id;
    private final String content;
    private final List<String> weightedContents;
    private final Emotion emotion;
    private final LocalDate date;
    private final LocalDateTime createdAt;
    private final Boolean isPublic;
    private final UserFixture userFixture;

    DiaryFixture(
            String content,
            List<String> weightedContents,
            Emotion emotion,
            LocalDate date,
            LocalDateTime createdAt,
            Boolean isPublic,
            UserFixture userFixture
    ) {
        this.id = DomainId.generate();
        this.content = content;
        this.weightedContents = weightedContents;
        this.emotion = emotion;
        this.date = date;
        this.createdAt = createdAt;
        this.isPublic = isPublic;
        this.userFixture = userFixture;
    }

    public DiaryBasic getDiaryBasie() {
        return new DiaryBasicBuilder()
                .id(id)
                .writerId(userFixture.getId())
                .content(content)
                .emotion(emotion)
                .date(date)
                .createdAt(createdAt)
                .isPublic(isPublic)
                .build();
    }

    public DiaryOverview getDiaryOverview() {
        return new DiaryOverviewBuilder()
                .id(id)
                .writerId(userFixture.getId())
                .content(content)
                .emotion(emotion)
                .date(date)
                .createdAt(createdAt)
                .isPublic(isPublic)
                .images(
                        Arrays.stream(ImageFixture.values())
                                .filter(imageFixture -> imageFixture.getDiaryFixture().equals(this))
                                .map(ImageFixture::getImage)
                                .toList())
                .build();
    }

    public DiaryComplete getDiaryComplete() {
        return new DiaryCompleteBuilder()
                .id(id)
                .writerId(userFixture.getId())
                .content(content)
                .weightedContents(weightedContents)
                .emotion(emotion)
                .date(date)
                .createdAt(createdAt)
                .isPublic(isPublic)
                .images(
                        Arrays.stream(ImageFixture.values())
                                .filter(imageFixture -> imageFixture.getDiaryFixture().equals(this))
                                .map(ImageFixture::getImage)
                                .toList())
                .likes(
                        Arrays.stream(LikeFixture.values())
                                .filter(likeFixture -> likeFixture.getDiaryFixture().equals(this))
                                .map(LikeFixture::getLike)
                                .toList())
                .build();
    }

    public static List<DiaryBasic> getAllDiaryBasicByUser(UserFixture userFixture) {
        return Arrays.stream(DiaryFixture.values())
                .filter(diaryFixture -> diaryFixture.userFixture.equals(userFixture))
                .map(DiaryFixture::getDiaryBasie)
                .toList();
    }

    public static List<DiaryOverview> getLikedDiaries(UserFixture userFixture) {
        return Arrays.stream(LikeFixture.values())
                .filter(likeFixture -> likeFixture.getUserFixture().equals(userFixture))
                .map(LikeFixture::getDiaryFixture)
                .map(DiaryFixture::getDiaryOverview)
                .toList();
    }
}
