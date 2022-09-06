package com.extendablechattingbe.extendablechattingbe.common.error;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessage;

import lombok.Getter;

@Getter
public abstract class ApiException extends RuntimeException {
    private final ResponseMessage error;

    public ApiException(ResponseMessage responseMessage) {
        super(responseMessage.getMessage());
        this.error = responseMessage;
    }
}