package com.canvas.bootstrap.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "회원가입 응답")
@AllArgsConstructor
@Getter
public class RegisterUserResponse {
    @Schema(description = "access token")
    private final String accessToken;
    @Schema(description = "refresh token")
    private final String refreshToken;
}
