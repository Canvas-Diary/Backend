package com.canvas.application.user.port.out;

import com.canvas.application.user.vo.UserClaim;

public interface UserTokenConvertPort {

    String createAccessToken(String userId);

    String createRefreshToken(String userId);
    UserClaim resolveAccessToken(String token);
    UserClaim resolveRefreshToken(String token);
}
