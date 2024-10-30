package com.canvas.application.user.port.out;

import com.canvas.application.user.vo.OauthUserInfo;

public interface AuthInfoRetrievePort {
    String getAccessToken(String provider, String code);

    OauthUserInfo getUserInfo(String provider, String accessToken);
}
