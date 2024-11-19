package com.canvas.bootstrap.common.filter.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends BusinessException {

    private static final String CODE_PREFIX = "AUTH";
    private static final String DEFAULT_MESSAGE = "인증 예외가 발생했습니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public AuthenticationException() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public AuthenticationException(int errorCode, HttpStatus httpStatus, String message) {
        super(CODE_PREFIX, errorCode, httpStatus, message);
    }

}
