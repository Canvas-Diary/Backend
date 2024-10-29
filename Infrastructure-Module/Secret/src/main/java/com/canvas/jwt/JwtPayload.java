package com.canvas.jwt;

import java.util.Date;
import java.util.Map;

public record JwtPayload(
        Date issuedAt,
        String issuer,
        String subject,
        Long expireTime,
        JwtClaims claims
) {

    static Map<String, Object> getClaims(JwtClaims claims) {
        return claims.toMap();
    }
}
