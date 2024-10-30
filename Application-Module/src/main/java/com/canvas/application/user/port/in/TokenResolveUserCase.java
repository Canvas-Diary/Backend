package com.canvas.application.user.port.in;

import com.canvas.application.user.vo.UserClaim;

public interface TokenResolveUserCase {

    UserClaim resolveToken(String accessToken);
}
