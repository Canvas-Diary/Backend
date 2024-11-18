package com.canvas.openai.gpt.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class GptException extends BusinessException {

    private static final String CODE_PREFIX = "GPT";
    private static final String DEFAULT_MESSAGE = "GPT 예외가 발생했습니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public GptException() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public GptException(int errorCode, HttpStatus httpStatus, String message) {
        super(CODE_PREFIX, errorCode, httpStatus, message);
    }

    public static class GptSafetyException extends GptException {
        public GptSafetyException() {
            super(1, HttpStatus.BAD_REQUEST, "유해한 내용입니다.");
        }
    }

    public static class GptTooManyRequestsException extends GptException {
        public GptTooManyRequestsException() {
            super(2, HttpStatus.TOO_MANY_REQUESTS, "요청이 너무 많습니다.");
        }
    }

}