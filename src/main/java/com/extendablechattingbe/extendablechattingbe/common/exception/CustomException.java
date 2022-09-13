package com.extendablechattingbe.extendablechattingbe.common.exception;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessages;
import com.extendablechattingbe.extendablechattingbe.common.response.SimpleResponseDTO;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final SimpleResponseDTO simpleResponseDTO;

    public CustomException(ResponseMessages responseMessages) {
        super(responseMessages.getMessage());
        this.simpleResponseDTO = SimpleResponseDTO.of(responseMessages);
    }
}