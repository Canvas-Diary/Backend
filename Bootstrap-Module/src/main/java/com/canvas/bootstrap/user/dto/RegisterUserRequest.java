package com.canvas.bootstrap.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 요청")
public record RegisterUserRequest(
        @Schema(description = "소셜 로그인 제공자")
        String provider,

        @Schema(description = "소셜 로그인 제공자 토큰")
        String socialLoginProviderToken
) {
}