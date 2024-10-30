package com.canvas.oauth;

import com.canvas.application.user.vo.OauthUserInfo;

public interface OAuthRetrieveHandler {

    String getAccessToken(String code);

    OauthUserInfo getUserInfo(String accessToken);

    record OAuthAccessTokenResponse(
            String accessToken
    ){}

    record OAuthUserInfoResponse(
            String socialId,
            String username
    ) {}
}
