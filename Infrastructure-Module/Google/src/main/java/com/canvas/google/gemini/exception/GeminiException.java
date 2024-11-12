package com.canvas.google.gemini.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class GeminiException extends BusinessException {

    private static final String CODE_PREFIX = "GEMINI";
    private static final String DEFAULT_MESSAGE = "Gemini 예외가 발생했습니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public GeminiException() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public GeminiException(int errorCode, HttpStatus httpStatus, String message) {
        super(CODE_PREFIX, errorCode, httpStatus, message);
    }

    public static class GeminiSafetyException extends GeminiException {
        public GeminiSafetyException() {
            super(1, HttpStatus.BAD_REQUEST, "유해한 내용입니다.");
        }
    }

    public static class GeminiTooManyRequestsException extends GeminiException {
        public GeminiTooManyRequestsException() {
            super(2, HttpStatus.TOO_MANY_REQUESTS, "요청이 너무 많습니다.");
        }
    }


}
