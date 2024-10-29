package com.canvas.bootstrap.auth.dto;

public record ReissueResponse (
    String accessToken,
    String refreshToken
) {
}

