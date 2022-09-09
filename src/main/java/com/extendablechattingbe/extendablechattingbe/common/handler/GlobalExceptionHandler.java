package com.extendablechattingbe.extendablechattingbe.common.handler;


import com.extendablechattingbe.extendablechattingbe.common.ResponseMessages;
import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.common.response.SimpleResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<SimpleResponse> handleCustomException(final CustomException e) {
        e.printStackTrace();
        return buildResponseEntity(e.getSimpleResponse());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<SimpleResponse>handleBadRequestException(final IllegalArgumentException e){
        e.printStackTrace();
        SimpleResponse simpleResponse= SimpleResponse.of(ResponseMessages.BAD_REQUEST_ERROR);
        simpleResponse.setMessage(e.getMessage());
        return buildResponseEntity(simpleResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<SimpleResponse> handleException(final Exception e) {
        e.printStackTrace();
        return buildResponseEntity(new SimpleResponse(INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<SimpleResponse> buildResponseEntity(SimpleResponse simpleResponse){
        return ResponseEntity.status(simpleResponse.getStatus()).body(simpleResponse);
    }
}