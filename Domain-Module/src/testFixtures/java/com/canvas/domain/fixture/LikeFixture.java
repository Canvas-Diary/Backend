package com.canvas.domain.fixture;

import com.canvas.domain.builder.LikeBuilder;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Like;
import lombok.Getter;

import static com.canvas.domain.fixture.DiaryFixture.*;
import static com.canvas.domain.fixture.UserFixture.*;

public enum LikeFixture {
    PUBLIC_MY_DIARY_LIKE1(MYSELF, PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_LIKE2(OTHER1, PUBLIC_MY_DIARY),
    PUBLIC_MY_DIARY_LIKE3(OTHER2, PUBLIC_MY_DIARY),
    PUBLIC_OTHER_DIARY_LIKE1(MYSELF, PUBLIC_OTHER_DIARY),
    PUBLIC_OTHER_DIARY_LIKE2(OTHER2, PUBLIC_OTHER_DIARY);

    private final DomainId id;
    @Getter
    private final UserFixture userFixture;
    @Getter
    private final DiaryFixture diaryFixture;

    LikeFixture(UserFixture userFixture, DiaryFixture diaryFixture) {
        this.id = DomainId.generate();
        this.userFixture = userFixture;
        this.diaryFixture = diaryFixture;
    }

    public Like getLike() {
        return new LikeBuilder()
                .id(id)
                .userId(userFixture.getId())
                .diaryId(diaryFixture.getId())
                .build();
    }
}
