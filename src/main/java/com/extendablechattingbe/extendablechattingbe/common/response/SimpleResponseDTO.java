package com.extendablechattingbe.extendablechattingbe.common.response;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SimpleResponseDTO {
    private int code;
    private HttpStatus status;
    private String message;

    private SimpleResponseDTO(ResponseMessages code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }
    private SimpleResponseDTO(ResponseMessages code,String message){
        this(code);
        this.message=message;
    }
    public static SimpleResponseDTO of(ResponseMessages responseMessages) {
        return new SimpleResponseDTO(responseMessages);
    }
    public static SimpleResponseDTO of(ResponseMessages responseMessages,String message) {
        return new SimpleResponseDTO(responseMessages,message);
    }
}