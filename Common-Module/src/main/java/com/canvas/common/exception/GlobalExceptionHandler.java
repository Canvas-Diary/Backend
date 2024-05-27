package com.canvas.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<DefaultErrorResponse> handleBusinessException(BusinessException e) {
        return new ResponseEntity<>(new DefaultErrorResponse(
                e.getCode(),
                e.getErrorMessage()
        ), e.getHttpStatus());
    }
}
