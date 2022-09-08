package com.extendablechattingbe.extendablechattingbe.common.handler;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessage.INTERNAL_SERVER_ERROR;

import com.extendablechattingbe.extendablechattingbe.common.error.ApiException;
import com.extendablechattingbe.extendablechattingbe.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ApiResponse<Object> handleException(final Exception e) {
        e.printStackTrace();
        return ApiResponse.error(INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    protected ApiResponse<Object> handleApiException(final ApiException e) {
        e.printStackTrace();
        return ApiResponse.error(e.getError());
    }

}