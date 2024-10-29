package com.canvas.oauth;

import com.canvas.application.user.port.out.AuthInfoRetrievePort;
import com.canvas.application.user.vo.OauthUserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class KakaoOAuthRetrieveHandler implements OAuthRetrieveHandler, AuthInfoRetrievePort {

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String grant_type = "authorization_code";
    @Value("${KAKAO_REST_API_KEY}")
    private String clientId;
    @Value("${KAKAO_CLIENT_SECERT}")
    private String clientSecret;
    private String redirectUri = "http://localhost:8080/api/v1/auth/kakao/callback";

    @Override
    public String getAccessToken(String code) {
        OAuthAccessTokenResponse oAuthAccessTokenResponse = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .post()
                .uri("/oauth/token")
                .body(BodyInserters.fromFormData("grant_type", grant_type)
                        .with("client_id", clientId)
                        .with("redirect_uri", redirectUri)
                        .with("code", code)
                        .with("client_secret", clientSecret))
                .retrieve()
                .bodyToMono(KakaoAccessTokenResponse.class)
                .map(response -> new OAuthAccessTokenResponse(response.accessToken()))
                .block();
        return oAuthAccessTokenResponse.accessToken();
    }


    @Override
    public OauthUserInfo getUserInfo(String provider, String accessToken) {
        OAuthUserInfoResponse kakaoUserInfo = WebClient.builder()
                .baseUrl("https://kauth.kakao.com")
                .build()
                .get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .map(userInfo -> new OAuthUserInfoResponse(
                        userInfo.properties().nickname(),
                        userInfo.id(),
                        userInfo.kakaoAccount().email()))
                .block();

        return new OauthUserInfo(kakaoUserInfo.username(), kakaoUserInfo.socialId(), kakaoUserInfo.email());
    }


    record KakaoAccessTokenResponse(
            String tokenType,
            String accessToken,
            String expiresIn,
            String refreshToken,
            String refreshTokenExpiresIn
    ){}

    record KakaoUserInfo(
        String id,
        Properties properties,
        KakaoAccount kakaoAccount
    ) {

    }

    record Properties(
        String nickname
    ) { }

    record KakaoAccount(
        String email
    ) {

    }
}
