package com.extendablechattingbe.extendablechattingbe.common.dto;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessage;
import java.net.http.HttpResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {


    private int code;
    private HttpStatus status;
    private String message;
    private T data;

    public static final ApiResponse<String> SUCCESS = success("");

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(200, HttpStatus.OK, "", data);
    }

    public static <T> ApiResponse<T> success(ResponseMessage responseMessage) {
        return new ApiResponse<>(responseMessage.getCode(), responseMessage.getStatus(), responseMessage.getMessage(), null);
    }


    public static <T> ApiResponse<T> success(ResponseMessage responseMessage, T data) {
        return new ApiResponse<>(responseMessage.getCode(), responseMessage.getStatus(), responseMessage.getMessage(), data);
    }

    public static <T> ApiResponse<T> error(ResponseMessage responseMessage) {
        return new ApiResponse<>(responseMessage.getCode(), responseMessage.getStatus(), responseMessage.getMessage(), null);
    }

}
