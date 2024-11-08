package com.canvas.persistence.jpa.fixture;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.builder.UserEntityBuilder;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import lombok.Getter;

import java.util.UUID;

public enum UserEntityFixture {

    MYSELF("mySocialId", "myUsername", "KAKAO"),
    OTHER1("socialId1", "username1", "KAKAO"),
    OTHER2("socialId2", "username2", "KAKAO");

    @Getter
    private final UUID id;
    private final String socialId;
    private final String username;
    private final String socialLoginProvider;

    UserEntityFixture(String socialId, String username, String socialLoginProvider) {
        this.id = DomainId.generate().value();
        this.socialId = socialId;
        this.username = username;
        this.socialLoginProvider = socialLoginProvider;
    }

    public UserEntity getUserEntity() {
        return new UserEntityBuilder()
                .id(id)
                .socialId(socialId)
                .username(username)
                .socialLoginProvider(socialLoginProvider)
                .build();
    }

}
