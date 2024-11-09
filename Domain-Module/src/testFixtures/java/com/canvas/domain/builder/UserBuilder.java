package com.canvas.domain.builder;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.User;
import com.canvas.domain.user.enums.SocialLoginProvider;

public class UserBuilder {
    private DomainId id;
    private String username;
    private String socialId;
    private SocialLoginProvider socialLoginProvider;

    public UserBuilder id(DomainId id) {
        this.id = id;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder socialId(String socialId) {
        this.socialId = socialId;
        return this;
    }

    public UserBuilder socialLoginProvider(SocialLoginProvider socialLoginProvider) {
        this.socialLoginProvider = socialLoginProvider;
        return this;
    }

    public User build() {
        return User.create(id, username, socialId, socialLoginProvider);
    }
}
