package com.extendablechattingbe.extendablechattingbe.common.response;

import com.extendablechattingbe.extendablechattingbe.common.CustomMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SimpleResponseDTO {
    private int code;
    private HttpStatus status;
    private String message;

    private SimpleResponseDTO(CustomMessages code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }
    private SimpleResponseDTO(CustomMessages code, String message){
        this(code);
        this.message=message;
    }
    public static SimpleResponseDTO of(CustomMessages customMessages) {
        return new SimpleResponseDTO(customMessages);
    }
    public static SimpleResponseDTO of(CustomMessages customMessages, String message) {
        return new SimpleResponseDTO(customMessages,message);
    }
}