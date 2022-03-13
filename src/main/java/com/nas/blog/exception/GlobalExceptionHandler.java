package com.nas.blog.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.nas.blog.exception.ExceptionCode.*;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(final AuthenticationException e){
        log.error("AuthenticationServiceException", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.of(FAIL_LOGIN));
    }

    @ExceptionHandler(value = FieldException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(final FieldException e) {
        log.error("FieldException", e);
        CustomFieldError error = new CustomFieldError(e.getError(), e.getField(), e.getParams());
        return ResponseEntity.status(e.getError().getStatus()).body(ExceptionResponse.of(error));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.of(RUNTIME_EXCEPTION));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.of(RUNTIME_EXCEPTION));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception e) {
        log.error("Exception", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionResponse.of(INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> exceptionHandler(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse.of(e.getFieldErrors()));
    }
}
