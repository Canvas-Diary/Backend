package com.canvas.jwt.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class TokenException extends BusinessException {

    private static final String CODE_PREFIX = "TOKEN";
    private static final String DEFAULT_MESSAGE = "유효하지 않은 토큰입니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public TokenException() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public TokenException(int errorCode, HttpStatus httpStatus, String message) {
        super(CODE_PREFIX, errorCode, httpStatus, message);
    }

    public static class TokenExpiredException extends TokenException {
        public TokenExpiredException() {
            super(1, DEFAULT_HTTP_STATUS, "만료된 토큰입니다.");
        }
    }

    public static class TokenSignatureException extends TokenException {
        public TokenSignatureException() {
            super(2, DEFAULT_HTTP_STATUS, "서명이 올바르지 않습니다.");
        }
    }
}
