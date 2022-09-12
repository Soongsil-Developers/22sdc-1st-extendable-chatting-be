package com.extendablechattingbe.extendablechattingbe.common.handler;


import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.common.response.SimpleResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.BAD_REQUEST_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<SimpleResponseDTO> handleCustomException(final CustomException e) {
        //e.printStackTrace();
        return buildResponseEntity(e.getSimpleResponseDTO());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<SimpleResponseDTO>handleBadRequestException(final IllegalArgumentException e){
        //e.printStackTrace();
        SimpleResponseDTO simpleResponseDTO = SimpleResponseDTO.of(BAD_REQUEST_ERROR,e.getMessage());
        return buildResponseEntity(simpleResponseDTO);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<SimpleResponseDTO> handleException(final Exception e) {
        //e.printStackTrace();
        return buildResponseEntity(SimpleResponseDTO.of(INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<SimpleResponseDTO> buildResponseEntity(SimpleResponseDTO simpleResponseDTO){
        return ResponseEntity.status(simpleResponseDTO.getStatus()).body(simpleResponseDTO);
    }
}