package com.canvas.application.user.port.out;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.User;
import com.canvas.domain.user.enums.SocialLoginProvider;

public interface UserManagementPort {
    User save(User user);
    User getById(DomainId userId);
    void delete(User user);
    boolean existsBySocialIdAndProviderId(String socialId, SocialLoginProvider socialLoginProvider);

    User getBySocialIdAndProvider(String socialId, SocialLoginProvider SocialLoginProvider);
}
