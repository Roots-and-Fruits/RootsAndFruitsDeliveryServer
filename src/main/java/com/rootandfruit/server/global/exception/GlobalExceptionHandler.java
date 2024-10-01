package com.rootandfruit.server.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비즈니스 로직에서 발생한 예외 (언체크)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorType> handleBusinessException(CustomException e) {
        return ResponseEntity
                .status(e.getErrorType().getHttpStatus())
                .body(e.getErrorType());
    }

    // valid에서 발생한 예외
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorType> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(e.getStatusCode())
                .body(ErrorType.INVALID_HTTP_REQUEST_ERROR);
    }
}
