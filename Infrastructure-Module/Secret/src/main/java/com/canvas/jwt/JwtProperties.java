package com.canvas.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @Value("${jwt.secret}")
    private String secret;

    public final String ISSUER = "canvas-diary";
    public final String SUBJECT = "canvas-diary-auth";
    public final int ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 30;
    public final int REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 *  10;

}
