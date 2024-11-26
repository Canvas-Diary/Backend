package com.canvas.aws.s3;

import com.canvas.common.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class S3Excpetion extends BusinessException {

    private static final String CODE_PREFIX = "S3";
    private static final String DEFAULT_MESSAGE = "S3 예외가 발생했습니다.";
    private static final HttpStatus DEFAULT_HTTP_STATUS = org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

    public S3Excpetion() {
        super(CODE_PREFIX, 0, DEFAULT_HTTP_STATUS, DEFAULT_MESSAGE);
    }

    public S3Excpetion(int errorCode, HttpStatus httpStatus, String message) {
        super(CODE_PREFIX, errorCode, httpStatus, message);
    }

    public static class S3NotFoundException extends S3Excpetion {
        public S3NotFoundException() {
            super(1, HttpStatus.NOT_FOUND, "존재하지 않는 파일입니다.");
        }
    }

    public static class S3UploadException extends S3Excpetion {
        public S3UploadException() {
            super(2, HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }
    }

}
