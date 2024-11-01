package com.canvas.client;

import com.canvas.client.oauth.KakaoOAuthRetrieveHandler;
import com.canvas.client.oauth.OAuthRetrieveHandler;
import com.canvas.domain.user.enums.SocialLoginProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.canvas.client"})
@RequiredArgsConstructor
public class OauthConfig {

    private final KakaoOAuthRetrieveHandler kakaoOAuthRetrieveHandler;

    @Bean
    @Qualifier("oauthRetrieveHandlers")
    Map<SocialLoginProvider, OAuthRetrieveHandler> oauthRetrieveHandlers() {
        return Map.of(SocialLoginProvider.KAKAO, kakaoOAuthRetrieveHandler);
    }

}
