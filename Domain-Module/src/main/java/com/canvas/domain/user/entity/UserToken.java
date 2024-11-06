package com.canvas.domain.user.entity;

import com.canvas.domain.common.DomainId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserToken {
    private final DomainId domainId;
    private final String token;
    private final DomainId userId;

    public static UserToken create(DomainId domainId, String token, DomainId userId) {
        return new UserToken(domainId, token, userId);
    }
}
