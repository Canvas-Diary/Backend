package com.canvas.bootstrap.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "회원가입 요청")
@AllArgsConstructor
@Getter
public class RegisterUserRequest {
    @Schema(description = "소셜 로그인 제공자")
    private final String provider;
    @Schema(description = "소셜 로그인 제공자 토큰")
    private final String socialLoginProviderToken;
}
