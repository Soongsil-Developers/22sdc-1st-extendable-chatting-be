package com.extendablechattingbe.extendablechattingbe.common.error;

import static org.springframework.http.HttpStatus.*;

import org.springframework.web.bind.annotation.ResponseStatus;

import com.extendablechattingbe.extendablechattingbe.common.ResponseMessage;

@ResponseStatus(BAD_REQUEST)
public class BadRequestException extends ApiException {
	public BadRequestException(ResponseMessage responseMessage) {
		super(responseMessage);
	}
}
