package com.canvas.persistence.jpa.fixture;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.builder.LikeEntityBuilder;
import com.canvas.persistence.jpa.diary.entity.LikeEntity;
import lombok.Getter;

import java.util.UUID;

import static com.canvas.persistence.jpa.fixture.DiaryEntityFixture.*;
import static com.canvas.persistence.jpa.fixture.UserEntityFixture.*;

public enum LikeEntityFixture {

    PUBLIC_MY_DIARY_LIKE1(MYSELF, PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_LIKE2(OTHER1, PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_LIKE3(OTHER2, PUBLIC_MY_DIARY),
    PUBLIC_OTHER_DIARY_LIKE1(MYSELF, PUBLIC_OTHER_DIARY),
    PUBLIC_OTHER_DIARY_LIKE2(OTHER2, PUBLIC_OTHER_DIARY);

    private final UUID id;
    private final UserEntityFixture userEntityFixture;
    @Getter
    private final DiaryEntityFixture diaryEntityFixture;

    LikeEntityFixture(UserEntityFixture userEntityFixture, DiaryEntityFixture diaryEntityFixture) {
        this.id = DomainId.generate().value();
        this.userEntityFixture = userEntityFixture;
        this.diaryEntityFixture = diaryEntityFixture;
    }

    public LikeEntity getLikeEntity() {
        return new LikeEntityBuilder()
                .id(id)
                .userId(userEntityFixture.getId())
                .diaryId(diaryEntityFixture.getId())
                .build();
    }

}
