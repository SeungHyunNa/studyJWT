package com.nas.blog.exception;

import com.nas.blog.util.MessageManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomFieldError {
    private String field;
    private String reason;

    public CustomFieldError(String field, String reason) {
        this.field = field;
        this.reason = reason;
    }

    public CustomFieldError(ExceptionCode code, String field, String... params){
        this.field = field;
        this.reason = MessageManager.getMessage(code.getCode(), params);
    }

    public CustomFieldError(FieldError error){
        field = error.getField();
        reason = MessageManager.getMessage(error.getCode(),error.getField());
    }
}
