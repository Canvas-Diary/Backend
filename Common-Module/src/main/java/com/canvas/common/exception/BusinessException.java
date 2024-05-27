package com.canvas.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final String codePrefix;
    private final String code;
    private final HttpStatus httpStatus;
    private final String errorMessage;

    private static final String DEFAULT_MESSAGE = "예상하지 못한 예외가 발생했습니다.";
    private static final String DEFAULT_CODE_PREFIX = "UNKNOWN";

    public BusinessException(String codePrefix, int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.codePrefix = codePrefix;
        this.httpStatus = httpStatus;

        if(errorMessage == null || errorMessage.isEmpty()) {
            this.errorMessage = DEFAULT_MESSAGE;
        }else{
            this.errorMessage = errorMessage;
        }

        this.code = String.format("%s-%03d",codePrefix, errorCode);
    }


}
