package com.canvas.persistence.jpa.user;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.User;
import com.canvas.domain.user.enums.SocialLoginProvider;
import com.canvas.persistence.jpa.user.entity.UserEntity;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getDomainId().value(),
                user.getEmail(),
                user.getUsername(),
                user.getSocialLoginProvider().name()
        );
    }

    public static User toDomain(UserEntity userEntity) {
        return User.withId(
                new DomainId(userEntity.getId()),
                userEntity.getEmail(),
                userEntity.getUsername(),
                SocialLoginProvider.parse(userEntity.getSocialLoginProvider())
        );
    }
}
