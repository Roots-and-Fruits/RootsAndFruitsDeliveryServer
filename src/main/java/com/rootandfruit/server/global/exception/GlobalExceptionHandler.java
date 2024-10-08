package com.rootandfruit.server.global.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
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

    // JWT 관련 예외 처리 (JwtException을 상속받는 모든 예외 처리)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorType> handleJwtException() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ErrorType.JWT_AUTHORIZATION_FAILED);
    }
}
