package com.canvas.jwt;

import java.util.HashMap;
import java.util.Map;

public record JwtClaims(
        String userId,
        TokenType tokenType
) {

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId());
        map.put("tokenType", tokenType().name());
        return map;
    }
}
