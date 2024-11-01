package com.canvas.domain.user.enums;


import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public enum SocialLoginProvider {
    KAKAO("kakao");

    private final String value;

    public static SocialLoginProvider parse(String value) {
        return Arrays.stream(SocialLoginProvider.values())
                .filter(p -> p.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 소셜 로그인 제공자"));
    }
}
