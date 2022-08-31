package com.extendablechattingbe.extendablechattingbe.error.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.error.ErrorCode;

@ResponseStatus(CONFLICT)
public class ConflictException extends ApiException {
	public ConflictException(ErrorCode errorCode) {
		super(errorCode);
	}
}
