package com.canvas.application.diary.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DiaryException extends BusinessException {

    private static final String CODE_PREFIX = "DIARY";
    private static final String DEFAULT_MESSAGE = "일기 예외가 발생했습니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public DiaryException() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public DiaryException(int errorCode, HttpStatus httpStatus, String message) {
        super(CODE_PREFIX, errorCode, httpStatus, message);
    }

    public static class DiaryNotFoundException extends DiaryException {
        public DiaryNotFoundException() {
            super(1, HttpStatus.NOT_FOUND, "존재하지 않는 일기입니다.");
        }
    }

    public static class DiaryForbiddenException extends DiaryException {
        public DiaryForbiddenException() {
            super(2, HttpStatus.FORBIDDEN, "접근 권한이 없는 일기입니다.");
        }
    }

}
