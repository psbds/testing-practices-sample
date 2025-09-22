package com.github.psbds.errors.exception;

import io.smallrye.common.constraint.Nullable;
import lombok.Getter;

@Getter
public class BusinessExceptionError {

    private @Nullable String field;
    private String message;

    public BusinessExceptionError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public BusinessExceptionError(String message) {
        this.message = message;
    }
}
