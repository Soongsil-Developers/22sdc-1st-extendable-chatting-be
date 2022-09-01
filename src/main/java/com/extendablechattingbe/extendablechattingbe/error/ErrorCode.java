package com.extendablechattingbe.extendablechattingbe.error;


import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// Global
	INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
    MEMBER_NOT_FOUND_ERROR(404, BAD_REQUEST,"해당 멤버는 존재하지 않습니다."),
	ROOM_NOT_FOUND_ERROR(404, BAD_REQUEST,"해당 방은 존재하지 않습니다."),
	;


	private final int value;
	private final HttpStatus status;
	private final String message;

}
