package com.canvas.oauth;

import com.canvas.application.user.vo.OauthUserInfo;

public interface OAuthRetrieveHandler {

    String getAccessToken(String code);

    OauthUserInfo getUserInfo(String provider, String accessToken);

    record OAuthAccessTokenResponse(
            String accessToken
    ){}

    record OAuthUserInfoResponse(
            String username,
            String socialId,
            String email
    ) {}
}
