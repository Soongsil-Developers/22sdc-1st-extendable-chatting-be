package com.extendablechattingbe.extendablechattingbe.common.error.exception;

import static org.springframework.http.HttpStatus.*;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessage;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(NOT_FOUND)
public class NotFoundException extends ApiException {
	public NotFoundException(ResponseMessage responseMessage) {
		super(responseMessage);
	}
}
