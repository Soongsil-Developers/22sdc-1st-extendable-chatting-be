package com.extendablechattingbe.extendablechattingbe.error.exception;

import com.extendablechattingbe.extendablechattingbe.error.ErrorCode;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}