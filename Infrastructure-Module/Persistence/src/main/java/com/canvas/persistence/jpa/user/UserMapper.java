package com.canvas.persistence.jpa.user;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.User;
import com.canvas.domain.user.entity.UserToken;
import com.canvas.domain.user.enums.SocialLoginProvider;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import com.canvas.persistence.jpa.user.entity.UserTokenEntity;

public class UserMapper {
    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId().value(),
                user.getUsername(),
                user.getSocialId(),
                user.getSocialLoginProvider().name()
        );
    }

    public static UserTokenEntity toEntity(UserToken userToken) {
        return new UserTokenEntity(
                userToken.getDomainId().value(),
                userToken.getToken(),
                userToken.getUserId().value()
        );
    }

    public static User toDomain(UserEntity userEntity) {
        return User.create(
                new DomainId(userEntity.getId()),
                userEntity.getUsername(),
                userEntity.getSocialId(),
                SocialLoginProvider.parse(userEntity.getSocialLoginProvider())
        );
    }

    public static UserToken toDomain(UserTokenEntity userTokenEntity) {
        return UserToken.create(
                new DomainId(userTokenEntity.getId()),
                userTokenEntity.getToken(),
                new DomainId(userTokenEntity.getUserId())
        );
    }
}
