package com.canvas.bootstrap.auth.dto;

public record LogoutRequest(
    String refreshToken
) {
}
