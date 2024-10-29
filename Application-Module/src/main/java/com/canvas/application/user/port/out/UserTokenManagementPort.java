package com.canvas.application.user.port.out;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.UserToken;

public interface UserTokenManagementPort {

    UserToken save(UserToken userToken);
    void deleteByTokenAndUserId(String token, DomainId userId);
    boolean existsByToken(String token);
}
