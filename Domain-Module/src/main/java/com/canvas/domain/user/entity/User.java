package com.canvas.domain.user.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.enums.SocialLoginProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class User {
    private DomainId id;
    private String username;
    private String socialId;
    private SocialLoginProvider socialLoginProvider;

    public static User create(DomainId id, String username, String socialId, SocialLoginProvider socialLoginProvider) {
        return new User(id, username, socialId, socialLoginProvider);
    }
}
