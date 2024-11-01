package com.canvas.client.oauth;

import com.canvas.application.user.vo.OauthUserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoOAuthRetrieveHandler implements OAuthRetrieveHandler {

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String grant_type = "authorization_code";
    @Value("${kakao.rest-api-key}")
    private String clientId;
    @Value("${kakao.client-secret}")
    private String clientSecret;
    private static final String redirectUri = "http://localhost:8080/api/v1/auth/kakao/callback";

    @Override
    public String getAccessToken(String code) {
        return WebClient.builder()
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
                .block().accessToken();
    }


    @Override
    public OauthUserInfo getUserInfo(String accessToken) {
         return WebClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .map(userInfo -> new OauthUserInfo(
                        userInfo.id(),
                        userInfo.properties().nickname()
                        ))
                .block();
    }


    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    record KakaoAccessTokenResponse(
            String tokenType,
            String accessToken,
            String expiresIn,
            String refreshToken,
            String refreshTokenExpiresIn
    ){}

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    record KakaoUserInfo(
        String id,
        Properties properties
    ) {}

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    record Properties(
        String nickname
    ) {}

}
