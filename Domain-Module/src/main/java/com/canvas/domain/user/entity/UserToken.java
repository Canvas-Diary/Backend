package com.canvas.domain.user.entity;

import com.canvas.domain.common.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserToken {
    private final DomainId domainId;
    private final String token;
    private final DomainId userId;
}
