package com.extendablechattingbe.extendablechattingbe.common.response;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessages;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class SimpleResponse {
    private int code;
    private HttpStatus status;
    private String message;

    public SimpleResponse(ResponseMessages code) {
        this.message = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }
    public static SimpleResponse of(ResponseMessages responseMessages) {
        return new SimpleResponse(responseMessages);
    }
}