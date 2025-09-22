package com.github.psbds.errors.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private String errorCode;
    private String errorMessage;
    private List<BusinessExceptionError> errors = new ArrayList<>();

    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorCode, String errorMessage, List<BusinessExceptionError> errors) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

}
