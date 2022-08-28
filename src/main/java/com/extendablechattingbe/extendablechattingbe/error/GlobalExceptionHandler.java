package com.extendablechattingbe.extendablechattingbe.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.extendablechattingbe.extendablechattingbe.error.exception.ApiException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
		e.printStackTrace();
		return ResponseEntity
			.status(INTERNAL_SERVER_ERROR)
			.body(ErrorResponse.of(500, INTERNAL_SERVER_ERROR, e.getMessage()));
	}

	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<ErrorResponse> handleApiException(final ApiException e) {
		e.printStackTrace();
		ErrorCode errorCode = e.getErrorCode();
		return ResponseEntity
			.status(errorCode.getStatus())
			.body(ErrorResponse.of(errorCode));
	}

}
