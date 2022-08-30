package com.extendablechattingbe.extendablechattingbe.error.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.error.ErrorCode;

@ResponseStatus(BAD_REQUEST)
public class BadRequestException extends ApiException {
	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}
}
