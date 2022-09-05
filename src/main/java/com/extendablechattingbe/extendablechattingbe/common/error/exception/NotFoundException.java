package com.extendablechattingbe.extendablechattingbe.common.error.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.common.ErrorCode;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends ApiException {
	public NotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
