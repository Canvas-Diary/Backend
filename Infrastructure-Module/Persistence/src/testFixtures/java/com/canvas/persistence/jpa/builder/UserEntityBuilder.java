package com.canvas.persistence.jpa.builder;

import com.canvas.persistence.jpa.user.entity.UserEntity;

import java.util.UUID;

public class UserEntityBuilder {

    private UUID id;
    private String socialId;
    private String username;
    private String socialLoginProvider;

    public UserEntityBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public UserEntityBuilder socialId(String socialId) {
        this.socialId = socialId;
        return this;
    }

    public UserEntityBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserEntityBuilder socialLoginProvider(String socialLoginProvider) {
        this.socialLoginProvider = socialLoginProvider;
        return this;
    }

    public UserEntity build() {
        return new UserEntity(
                this.id,
                this.socialId,
                this.username,
                this.socialLoginProvider
        );
    }

}
