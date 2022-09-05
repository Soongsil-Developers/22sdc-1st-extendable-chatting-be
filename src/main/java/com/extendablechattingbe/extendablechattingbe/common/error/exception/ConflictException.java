package com.extendablechattingbe.extendablechattingbe.common.error.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.common.ErrorCode;

@ResponseStatus(CONFLICT)
public class ConflictException extends ApiException {
	public ConflictException(ErrorCode errorCode) {
		super(errorCode);
	}
}
