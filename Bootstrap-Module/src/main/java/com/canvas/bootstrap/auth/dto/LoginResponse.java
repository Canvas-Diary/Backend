package com.canvas.bootstrap.auth.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
