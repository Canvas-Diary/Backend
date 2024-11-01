package com.canvas.jwt;

import com.canvas.application.user.port.out.UserTokenConvertPort;
import com.canvas.application.user.vo.UserClaim;
import com.canvas.jwt.exception.TokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserTokenConvertAdapter implements UserTokenConvertPort {

    private final JwtProperties jwtProperties;

    @Override
    public String createAccessToken(String userId) {
        return Jwts.builder()
                .issuer(jwtProperties.ISSUER)
                .subject(jwtProperties.SUBJECT)
                .claims(JwtPayload.getClaims(new JwtClaims(userId, TokenType.ACCESS_TOKEN)))
                .issuedAt(new Date())
                .signWith(generateKey(jwtProperties.getSecret()))
                .expiration(new Date(new Date().getTime() + jwtProperties.ACCESS_TOKEN_EXPIRED_TIME))
                .compact();
    }

    @Override
    public String createRefreshToken(String userId) {
        return Jwts.builder()
                .issuer(jwtProperties.ISSUER)
                .subject(jwtProperties.SUBJECT)
                .claims(JwtPayload.getClaims(new JwtClaims(userId, TokenType.REFRESH_TOKEN)))
                .issuedAt(new Date())
                .signWith(generateKey(jwtProperties.getSecret()))
                .expiration(new Date(new Date().getTime() + jwtProperties.REFRESH_TOKEN_EXPIRED_TIME))
                .compact();
    }

    @Override
    public UserClaim resolveAccessToken(String token) {
        try {
            Claims claims = Jwts.parser().verifyWith(generateKey(jwtProperties.getSecret())).build().parseSignedClaims(token).getPayload();
            return new UserClaim(claims.get("userId", String.class));
        } catch (JwtException e) {
            if (e instanceof SignatureException) {
                throw new TokenException.TokenSignatureException();
            }
            else if (e instanceof ExpiredJwtException) {
                throw new TokenException.TokenExpiredException();
            }
            else {
                throw new TokenException();
            }
        }
    }

    @Override
    public UserClaim resolveRefreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(generateKey(jwtProperties.getSecret()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return new UserClaim(claims.get("userId", String.class));
        } catch (JwtException e) {
            if (e instanceof SignatureException) {
                throw new TokenException.TokenSignatureException();
            }
            else if (e instanceof ExpiredJwtException) {
                throw new TokenException.TokenExpiredException();
            }
            else {
                throw new TokenException();
            }
        }
    }

    private SecretKey generateKey(String secretKey) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
