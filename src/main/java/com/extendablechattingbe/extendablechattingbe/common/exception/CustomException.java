package com.extendablechattingbe.extendablechattingbe.common.exception;

import com.extendablechattingbe.extendablechattingbe.common.response.SimpleResponse;
import com.extendablechattingbe.extendablechattingbe.common.ResponseMessages;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final SimpleResponse simpleResponse;

    public CustomException(ResponseMessages responseMessages) {
        super(responseMessages.getMessage());
        this.simpleResponse = SimpleResponse.of(responseMessages);
    }
}