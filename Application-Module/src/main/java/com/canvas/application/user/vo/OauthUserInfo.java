package com.canvas.application.user.vo;

public record OauthUserInfo(
    String username,
    String socialId,
    String email
) {
}
