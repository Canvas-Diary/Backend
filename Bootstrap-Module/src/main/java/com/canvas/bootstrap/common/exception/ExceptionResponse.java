package com.canvas.bootstrap.common.exception;

import com.canvas.common.exception.BusinessException;

public record ExceptionResponse(
        String code,
        String message
) {
    public static ExceptionResponse of(BusinessException e) {
        return new ExceptionResponse(e.getErrorCode(), e.getMessage());
    }
}
