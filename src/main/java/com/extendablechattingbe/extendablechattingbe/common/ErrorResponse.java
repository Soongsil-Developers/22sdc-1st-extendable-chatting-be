package com.extendablechattingbe.extendablechattingbe.common;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	private int value;
	private HttpStatus status;
	private String message;

	public static ErrorResponse of(int value, HttpStatus status, String message) {
		return new ErrorResponse(value, status, message);
	}
	public static ErrorResponse of(ErrorCode errorCode) {
		int value = errorCode.getValue();
		HttpStatus status = errorCode.getStatus();
		String message = errorCode.getMessage();
		return new ErrorResponse(value, status, message);
	}
}
