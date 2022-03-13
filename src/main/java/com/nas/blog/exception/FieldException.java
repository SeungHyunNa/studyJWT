package com.nas.blog.exception;

import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public class FieldException extends IllegalArgumentException {
    private final ExceptionCode error;
    private final String field;
    private String[] params;

    public FieldException(ExceptionCode error, String field, String... params) {
        super("An exception occurred because the field value was entered incorrectly. Field: " + field);
        this.error = error;
        this.field = field;
        this.params = ObjectUtils.isEmpty(params) ? new String[]{field} : params;
    }
}
