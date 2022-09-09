package com.extendablechattingbe.extendablechattingbe.common.response;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessages;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SimpleResponseDTO {
    private int code;
    private HttpStatus status;
    private String message;

    private SimpleResponseDTO(ResponseMessages code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }
    public static SimpleResponseDTO of(ResponseMessages responseMessages) {
        return new SimpleResponseDTO(responseMessages);
    }
}