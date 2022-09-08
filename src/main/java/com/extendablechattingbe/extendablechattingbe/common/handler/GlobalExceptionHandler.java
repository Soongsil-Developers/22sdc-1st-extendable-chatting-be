package com.extendablechattingbe.extendablechattingbe.common.handler;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessage.INTERNAL_SERVER_ERROR;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessage;
import com.extendablechattingbe.extendablechattingbe.common.error.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseMessage> handleException(final Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR.getStatus()).body(INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ResponseMessage> handleApiException(final ApiException e) {
        e.printStackTrace();
        ResponseMessage error=e.getError();
        return ResponseEntity.status(error.getStatus()).body(error);
    }

}