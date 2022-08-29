package com.extendablechattingbe.extendablechattingbe.error.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.error.ErrorCode;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends ApiException {
	public NotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
