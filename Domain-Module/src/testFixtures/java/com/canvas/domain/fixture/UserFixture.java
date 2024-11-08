package com.canvas.domain.fixture;

import com.canvas.domain.builder.UserBuilder;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.User;
import com.canvas.domain.user.enums.SocialLoginProvider;
import lombok.Getter;

public enum UserFixture {

    MYSELF("mySocialId", "myUsername", SocialLoginProvider.KAKAO),
    OTHER1("socialId1", "username1", SocialLoginProvider.KAKAO),
    OTHER2("socialId2", "username2", SocialLoginProvider.KAKAO);

    @Getter
    private final DomainId id;
    private final String socialId;
    private final String username;
    private final SocialLoginProvider socialLoginProvider;

    UserFixture(String socialId, String username, SocialLoginProvider socialLoginProvider) {
        this.id = DomainId.generate();
        this.socialId = socialId;
        this.username = username;
        this.socialLoginProvider = socialLoginProvider;
    }

    public User getUser() {
        return new UserBuilder()
                .id(id)
                .socialId(socialId)
                .username(username)
                .socialLoginProvider(socialLoginProvider)
                .build();
    }
}
