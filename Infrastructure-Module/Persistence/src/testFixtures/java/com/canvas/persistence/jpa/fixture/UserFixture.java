package com.canvas.persistence.jpa.fixture;

import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.builder.UserEntityBuilder;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import lombok.Getter;

import java.util.UUID;

public enum UserFixture {

    MYSELF("myEmail", "myUsername", "KAKAO"),
    OTHER1("email1", "username1", "KAKAO"),
    OTHER2("email2", "username2", "KAKAO");

    @Getter
    private final UUID id;
    private final String email;
    private final String username;
    private final String socialLoginProvider;

    UserFixture(String email, String username, String socialLoginProvider) {
        this.id = DomainId.generate().value();
        this.email = email;
        this.username = username;
        this.socialLoginProvider = socialLoginProvider;
    }

    public UserEntity getUserEntity() {
        return new UserEntityBuilder()
                .id(id)
                .email(email)
                .username(username)
                .socialLoginProvider(socialLoginProvider)
                .build();
    }

}
