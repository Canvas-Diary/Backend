package com.canvas.oauth;

import com.canvas.domain.user.enums.SocialLoginProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"com.canvas.oauth"})
public class OauthConfig {

    @Bean
    Map<SocialLoginProvider, OAuthRetrieveHandler> oauthRetrieveHandlers() {
        return Map.of(SocialLoginProvider.KAKAO, new KakaoOAuthRetrieveHandler());
    }

}
