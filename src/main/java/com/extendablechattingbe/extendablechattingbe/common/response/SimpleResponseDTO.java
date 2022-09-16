package com.extendablechattingbe.extendablechattingbe.common.response;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class SimpleResponseDTO {

    @Schema(description = "에러 번호")
    private int code;

    @Schema(description = "에러 상태")
    private HttpStatus status;

    @Schema(description = "에러 내용")
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