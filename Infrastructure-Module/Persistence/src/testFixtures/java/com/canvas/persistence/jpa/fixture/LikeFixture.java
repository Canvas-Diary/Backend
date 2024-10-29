package com.canvas.persistence.jpa.fixture;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.builder.LikeEntityBuilder;
import com.canvas.persistence.jpa.diary.entity.LikeEntity;
import lombok.Getter;

import java.util.UUID;

import static com.canvas.persistence.jpa.fixture.DiaryFixture.*;
import static com.canvas.persistence.jpa.fixture.UserFixture.*;

public enum LikeFixture {

    PUBLIC_MY_DIARY_LIKE1(MYSELF, PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_LIKE2(OTHER1, PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_LIKE3(OTHER2, PUBLIC_MY_DIARY);

    private final UUID id;
    private final UserFixture userFixture;
    @Getter
    private final DiaryFixture diaryFixture;

    LikeFixture(UserFixture userFixture, DiaryFixture diaryFixture) {
        this.id = DomainId.generate().value();
        this.userFixture = userFixture;
        this.diaryFixture = diaryFixture;
    }

    public LikeEntity getLikeEntity() {
        return new LikeEntityBuilder()
                .id(id)
                .userId(userFixture.getId())
                .diaryId(diaryFixture.getId())
                .build();
    }

}
