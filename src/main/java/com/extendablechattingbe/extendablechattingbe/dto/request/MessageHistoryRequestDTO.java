package com.extendablechattingbe.extendablechattingbe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MessageHistoryRequestDTO {

	private Long memberId;

	private PageRequestDTO pageRequest;

}