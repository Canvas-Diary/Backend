package com.canvas.persistence.jpa.fixture;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.builder.DiaryEntityBuilder;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import lombok.Getter;

import java.util.Arrays;
import java.util.UUID;

import static com.canvas.persistence.jpa.fixture.UserFixture.*;

public enum DiaryFixture {
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
    private final UserFixture userFixture;

    DiaryFixture(String content, String emotion, Boolean isPublic, UserFixture userFixture) {
        this.id = DomainId.generate().value();
        this.content = content;
        this.emotion = emotion;
        this.isPublic = isPublic;
        this.userFixture = userFixture;
    }

    public DiaryEntity getDiaryEntity() {
        return new DiaryEntityBuilder()
                .id(id)
                .content(content)
                .emotion(emotion)
                .isPublic(isPublic)
                .writerId(userFixture.getId())
                .writer(userFixture.getUserEntity())
                .imageEntities(
                        Arrays.stream(ImageFixture.values())
                                .filter(imageFixture -> imageFixture.getDiaryFixture().equals(this))
                                .map(ImageFixture::getImageEntity)
                                .toList())
                .likeEntities(
                        Arrays.stream(LikeFixture.values())
                                .filter(likeFixture -> likeFixture.getDiaryFixture().equals(this))
                                .map(LikeFixture::getLikeEntity)
                                .toList())
                .build();

    }
}
