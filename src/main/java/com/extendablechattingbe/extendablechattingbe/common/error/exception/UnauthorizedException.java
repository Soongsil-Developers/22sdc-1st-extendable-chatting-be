package com.extendablechattingbe.extendablechattingbe.common.error.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.common.ErrorCode;

@ResponseStatus(UNAUTHORIZED)
public class UnauthorizedException extends ApiException {
	public UnauthorizedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
