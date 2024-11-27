package com.canvas.application.image.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class ImageException extends BusinessException {

    private static final String CODE_PREFIX = "IMAGE";
    private static final String DEFAULT_MESSAGE = "이미지 예외가 발생했습니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public ImageException() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public ImageException(int errorCode, HttpStatus httpStatus, String message) {
        super(CODE_PREFIX, errorCode, httpStatus, message);
    }

    public static class ImageNotFoundException extends ImageException {
        public ImageNotFoundException() {
            super(1, HttpStatus.NOT_FOUND, "존재하지 않는 이미지입니다.");
        }
    }

    public static class ImageDailyLimitExceededException extends ImageException {
        public ImageDailyLimitExceededException() {
            super(2, HttpStatus.TOO_MANY_REQUESTS, "이미지 생성 제한을 초과했습니다.");
        }
    }
}