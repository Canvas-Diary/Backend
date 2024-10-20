package com.canvas.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final String errorCode;
    private final HttpStatus httpStatus;

    public BusinessException(String codePrefix, int errorCode, HttpStatus httpStatus, String message) {
        super(message);
        this.errorCode = String.format("%s-%03d", codePrefix, errorCode);
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        return String.format(
                "BusinessException(message=%s, code=%s, httpStatus=%d",
                getMessage(),
                errorCode,
                httpStatus.value()
        );
    }
}
