package com.canvas.domain.diary.exception;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public sealed class DiaryException extends BusinessException{
    private static final String DEFAULT_CODE_PREFIX = "DIARY";
    public DiaryException(String codePrefix, int errorCode, HttpStatus httpStatus, String errorMessage) {
        super(codePrefix, errorCode, httpStatus, errorMessage);
    }

    public static final class ImageStoreFailException extends DiaryException{

        public ImageStoreFailException() {
            super(DEFAULT_CODE_PREFIX, 1, HttpStatus.INTERNAL_SERVER_ERROR, "이미지 저장 중 에러가 발생했습니다.");
        }

        public ImageStoreFailException(String message) {
            super(DEFAULT_CODE_PREFIX, 1, HttpStatus.INTERNAL_SERVER_ERROR, message);
        }
    }
}
