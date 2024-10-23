package com.canvas.application.like.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class LikeException extends BusinessException {

    private static final String CODE_PREFIX = "Like";
    private static final String DEFAULT_MESSAGE = "좋아요 예외가 발생했습니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public LikeException() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public LikeException(String codePrefix, int errorCode, HttpStatus httpStatus, String message) {
        super(codePrefix, errorCode, httpStatus, message);
    }

    public static class LikeNotFoundException extends LikeException {
        public LikeNotFoundException() {
            super(CODE_PREFIX, 1, HttpStatus.NOT_FOUND, "좋아요 기록을 찾을 수 없습니다.");
        }
    }

    public static class LikeAlreadyExistsException extends LikeException {
        public LikeAlreadyExistsException() {
            super(CODE_PREFIX, 2, HttpStatus.CONFLICT, "이미 좋아요를 누른 일기입니다.");
        }
    }
}
