package com.nas.blog.exception;

import com.nas.blog.util.MessageManager;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.validation.FieldError;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class ExceptionResponse {

    private String code;
    private String message;
    private List<CustomFieldError> errors;

    @Builder
    public ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(List<FieldError> fieldErrors) {
        errors = fieldErrors.stream().map(CustomFieldError::new).collect(Collectors.toList());
    }

    public ExceptionResponse(CustomFieldError customFieldError){
        errors = Arrays.asList(customFieldError);
    }

    public static ExceptionResponse of(ExceptionCode code, String... messageParams){
        return ExceptionResponse.builder()
                .code(code.getCode())
                .message(MessageManager.getMessage(code.getCode(), messageParams))
                .build();
    }

    public static ExceptionResponse of(List<FieldError> fieldErrors) {
        return new ExceptionResponse(fieldErrors);
    }

    public static ExceptionResponse of(CustomFieldError customFieldError){
        return new ExceptionResponse(customFieldError);
    }
}
