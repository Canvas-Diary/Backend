package com.canvas.domain.user.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.enums.SocialLoginProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private final DomainId domainId;
    private final String email;
    private final String username;
    private final SocialLoginProvider socialLoginProvider;

    public static User withoutId(String email, String username, SocialLoginProvider socialLoginProvider) {
        return new User(DomainId.generate(), email, username, socialLoginProvider);
    }

    public static User withId(DomainId domainId, String email, String username, SocialLoginProvider socialLoginProvider) {
        return new User(domainId, email, username, socialLoginProvider);
    }
}
