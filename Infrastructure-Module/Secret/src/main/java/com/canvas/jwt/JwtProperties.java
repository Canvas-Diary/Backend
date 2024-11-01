package com.canvas.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String secret = "a6lqVQUYJMMkgIC8vUVJn8Mn1Ee3dzyVc021u21v8TI=";

    public final String ISSUER = "canvas-diary";
    public final String SUBJECT = "canvas-diary-auth";
    public final int ACCESS_TOKEN_EXPIRED_TIME = 1000 * 60 * 30;
    public final int REFRESH_TOKEN_EXPIRED_TIME = 1000 * 60 * 60 * 24 *  10;

}
