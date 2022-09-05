package com.extendablechattingbe.extendablechattingbe.common.error;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessage;
import com.extendablechattingbe.extendablechattingbe.common.dto.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.extendablechattingbe.extendablechattingbe.common.error.exception.ApiException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ApiResponse<Object> handleException(final Exception e) {
		e.printStackTrace();
		return ApiResponse.error(ResponseMessage.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ApiException.class)
	protected ApiResponse<Object> handleApiException(final ApiException e) {
		e.printStackTrace();
		return ApiResponse.error(e.getError());
	}

}
