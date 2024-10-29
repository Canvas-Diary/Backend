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
    private final String socialId;
    private final SocialLoginProvider socialLoginProvider;
}
