package com.extendablechattingbe.extendablechattingbe.error.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.error.ErrorCode;

@ResponseStatus(FORBIDDEN)
public class ForbiddenException extends ApiException {
	public ForbiddenException(ErrorCode errorCode) {
		super(errorCode);
	}
}
