package com.canvas.domain.user.entity;

import com.canvas.domain.user.enums.SocialLoginProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private final Long id;
    private final String email;
    private final String username;
    private final SocialLoginProvider socialLoginProvider;

    public static User withoutId(String email, String username, SocialLoginProvider socialLoginProvider) {
        return new User(null, email, username, socialLoginProvider);
    }

    public static User withId(Long id, String email, String username, SocialLoginProvider socialLoginProvider) {
        return new User(id, email, username, socialLoginProvider);
    }
}
