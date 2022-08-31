package com.extendablechattingbe.extendablechattingbe.error.exception;

import com.extendablechattingbe.extendablechattingbe.error.ErrorCode;

public class RoomNotFoundException extends ApiException {
	public RoomNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}
}
