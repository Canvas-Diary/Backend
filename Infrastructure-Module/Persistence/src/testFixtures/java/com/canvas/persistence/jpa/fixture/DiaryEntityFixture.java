package com.canvas.persistence.jpa.fixture;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.builder.DiaryEntityBuilder;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.canvas.persistence.jpa.fixture.UserEntityFixture.*;

public enum DiaryEntityFixture {
    PUBLIC_MY_DIARY(
            "내용1",
            List.of("강조내용1"),
            "JOY",
            true,
            LocalDate.of(2024, 10, 15),
            MYSELF
    ),

    PUBLIC_OTHER_DIARY(
            "내용2",
            List.of(),
            "ANGER",
            true,
            LocalDate.of(2024, 10, 17),
            OTHER1
    ),

    PRIVATE_OTHER_DIARY(
            "내용3",
            List.of("강조내용3"),
            "SADNESS",
            false,
            LocalDate.of(2024, 10, 31),
            OTHER2
    );

    @Getter
    private final UUID id;
    private final String content;
    private final String weightedContents;
    private final String emotion;
    private final Boolean isPublic;
    private final LocalDate date;
    private final UserEntityFixture userEntityFixture;

    DiaryEntityFixture(String content, List<String> weightedContents, String emotion, Boolean isPublic, LocalDate date, UserEntityFixture userEntityFixture) {
        this.id = DomainId.generate().value();
        this.weightedContents = String.join(",", weightedContents);
        this.content = content;
        this.emotion = emotion;
        this.isPublic = isPublic;
        this.date = date;
        this.userEntityFixture = userEntityFixture;
    }

    public DiaryEntity getDiaryEntity() {
        return new DiaryEntityBuilder()
                .id(id)
                .content(content)
                .weightedContents(weightedContents)
                .emotion(emotion)
                .isPublic(isPublic)
                .date(date)
                .writerId(userEntityFixture.getId())
                .writer(userEntityFixture.getUserEntity())
                .imageEntities(
                        Arrays.stream(ImageEntityFixture.values())
                                .filter(imageEntityFixture -> imageEntityFixture.getDiaryEntityFixture().equals(this))
                                .map(ImageEntityFixture::getImageEntity)
                                .toList())
                .likeEntities(
                        Arrays.stream(LikeEntityFixture.values())
                                .filter(likeEntityFixture -> likeEntityFixture.getDiaryEntityFixture().equals(this))
                                .map(LikeEntityFixture::getLikeEntity)
                                .toList())
                .build();

    }
}
