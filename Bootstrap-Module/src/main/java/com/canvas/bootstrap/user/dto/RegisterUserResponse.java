package com.canvas.bootstrap.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답")

public record RegisterUserResponse(
        @Schema(description = "access token")
        String accessToken,
        @Schema(description = "refresh token")
        String refreshToken) {
}
