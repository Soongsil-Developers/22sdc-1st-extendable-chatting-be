package com.extendablechattingbe.extendablechattingbe.common.error.exception;

import static org.springframework.http.HttpStatus.*;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessage;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(FORBIDDEN)
public class ForbiddenException extends ApiException {
	public ForbiddenException(ResponseMessage responseMessage) {
		super(responseMessage);
	}
}
