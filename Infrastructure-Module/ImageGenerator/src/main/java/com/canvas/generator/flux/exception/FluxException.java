package com.canvas.generator.flux.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class FluxException extends BusinessException {

    private static final String CODE_PREFIX = "FLUX";
    private static final String DEFAULT_MESSAGE = "Flux 예외가 발생했습니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.BAD_REQUEST;

    public FluxException() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public FluxException(int errorCode, HttpStatus httpStatus, String message) {
        super(CODE_PREFIX, errorCode, httpStatus, message);
    }

    public static class FluxPendingException extends FluxException {
        public FluxPendingException() {
            super(1, HttpStatus.BAD_REQUEST, "보류 중입니다.");
        }
    }

    public static class FluxTaskNotFoundException extends FluxException {
        public FluxTaskNotFoundException() {
            super(2, HttpStatus.NOT_FOUND, "작업을 찾을 수 없습니다.");
        }
    }

    public static class FluxRequestModeratedException extends FluxException {
        public FluxRequestModeratedException() {
            super(3, HttpStatus.BAD_REQUEST, "요청이 검열되었습니다.");
        }
    }

    public static class FluxContentModeratedException extends FluxException {
        public FluxContentModeratedException() {
            super(4, HttpStatus.BAD_REQUEST, "생성된 콘텐츠가 검열되었습니다.");
        }
    }

    public static class FluxErrorException extends FluxException {
        public FluxErrorException() {
            super(5, HttpStatus.BAD_REQUEST, "알 수 없는 예외가 발생했습니다.");
        }
    }
}
