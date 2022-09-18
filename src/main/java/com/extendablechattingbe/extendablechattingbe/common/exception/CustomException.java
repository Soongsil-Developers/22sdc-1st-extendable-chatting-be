package com.extendablechattingbe.extendablechattingbe.common.exception;

import com.extendablechattingbe.extendablechattingbe.common.CustomMessages;
import com.extendablechattingbe.extendablechattingbe.common.response.SimpleResponseDTO;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final SimpleResponseDTO simpleResponseDTO;

    public CustomException(CustomMessages customMessages) {
        super(customMessages.getMessage());
        this.simpleResponseDTO = SimpleResponseDTO.of(customMessages);
    }
}