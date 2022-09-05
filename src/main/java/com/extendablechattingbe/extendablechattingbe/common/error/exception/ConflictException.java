package com.extendablechattingbe.extendablechattingbe.common.error.exception;

import static org.springframework.http.HttpStatus.*;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessage;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(CONFLICT)
public class ConflictException extends ApiException {
	public ConflictException(ResponseMessage responseMessage) {
		super(responseMessage);
	}
}
