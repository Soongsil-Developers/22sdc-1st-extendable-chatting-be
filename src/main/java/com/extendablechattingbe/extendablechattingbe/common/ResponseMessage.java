package com.extendablechattingbe.extendablechattingbe.common;


import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

	/**
	 * Success Message
	 */
	//200 OK
	SUCCESS_OK(200, OK, ""),

	//201 CREATED
	SUCCESS_CREATED(201, CREATED, ""),
	CREATED_ROOM(201, CREATED, "채팅방이 생성되었습니다."),

	//202 ACCEPTED
	SUCCESS_ACCEPTED(202, ACCEPTED, ""),

	// 204 NO_CONTENT
	SUCCESS_NO_CONTENT(204, NO_CONTENT, ""),

	/**
	 * Error Message
	 */
	//400 BAD REQUEST
	BAD_REQUEST_ERROR(400, BAD_REQUEST, ""),

	//401 UNAUTHORIZED
	UNAUTHORIZED_ERROR(401, UNAUTHORIZED, ""),

	//403 FORBIDDEN
	FORBIDDEN_ERROR(403, FORBIDDEN, ""),

	//404 NOT FOUND
	NOT_FOUND_ERROR(404, NOT_FOUND, ""),
    MEMBER_NOT_FOUND_ERROR(404, NOT_FOUND,"해당 멤버는 존재하지 않습니다."),
	ROOM_NOT_FOUND_ERROR(404, NOT_FOUND,"해당 방은 존재하지 않습니다."),

	//500 INTERNAL SERVER ERROR
	INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
	;


	private final int code;
	private final HttpStatus status;
	private final String message;
}
