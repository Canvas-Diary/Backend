package com.canvas.client.oauth;

import com.canvas.application.user.port.out.AuthInfoRetrievePort;
import com.canvas.application.user.vo.OauthUserInfo;
import com.canvas.domain.user.enums.SocialLoginProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthRetrieveAdapter implements AuthInfoRetrievePort {

    private final Map<SocialLoginProvider, OAuthRetrieveHandler> oauthRetrieveHandlers;

    @Override
    public String getAccessToken(String provider, String code) {
        OAuthRetrieveHandler oauthRetrieveHandler = getHandler(provider);
        return oauthRetrieveHandler.getAccessToken(code);
    }

    @Override
    public OauthUserInfo getUserInfo(String provider, String accessToken) {
        OAuthRetrieveHandler oauthRetrieveHandler = getHandler(provider);
        return oauthRetrieveHandler.getUserInfo(accessToken);
    }

    private OAuthRetrieveHandler getHandler(String provider) {
        return this.oauthRetrieveHandlers.get(SocialLoginProvider.parse(provider));
    }
}
