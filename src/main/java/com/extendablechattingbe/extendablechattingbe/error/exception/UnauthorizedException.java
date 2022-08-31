package com.extendablechattingbe.extendablechattingbe.error.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.error.ErrorCode;

@ResponseStatus(UNAUTHORIZED)
public class UnauthorizedException extends ApiException {
	public UnauthorizedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
