package com.canvas.domain.user.enums;

import java.util.Arrays;

public enum SocialLoginProvider {
    KAKAO;

    public static SocialLoginProvider parse(String value) {
        return Arrays.stream(SocialLoginProvider.values())
                .filter(p -> p.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 소셜 로그인 제공자"));
    }
}
