package com.canvas.persistence.jpa.fixture;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.builder.DiaryEntityBuilder;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static com.canvas.persistence.jpa.fixture.UserEntityFixture.*;

public enum DiaryEntityFixture {
    PUBLIC_MY_DIARY(
            "내용1",
            "감정1",
            true,
            MYSELF
    ),

    PUBLIC_OTHER_DIARY(
            "내용2",
            "감정2",
            true,
            OTHER1
    ),

    PRIVATE_OTHER_DIARY(
            "내용3",
            "감정3",
            false,
            OTHER2
    );

    @Getter
    private final UUID id;
    private final String content;
    private final String emotion;
    private final Boolean isPublic;
    private final UserEntityFixture userEntityFixture;

    DiaryEntityFixture(String content, String emotion, Boolean isPublic, UserEntityFixture userEntityFixture) {
        this.id = DomainId.generate().value();
        this.content = content;
        this.emotion = emotion;
        this.isPublic = isPublic;
        this.userEntityFixture = userEntityFixture;
    }

    public DiaryEntity getDiaryEntity() {
        return new DiaryEntityBuilder()
                .id(id)
                .content(content)
                .emotion(emotion)
                .isPublic(isPublic)
                .dateTime(LocalDateTime.now())
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
