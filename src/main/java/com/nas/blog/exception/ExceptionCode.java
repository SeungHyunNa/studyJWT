package com.nas.blog.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public enum ExceptionCode {
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002"),
    FAIL_LOGIN(HttpStatus.UNAUTHORIZED, "E0003"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "E0004"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S0001");

    private final HttpStatus status;
    private final String code;

    ExceptionCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }
}
