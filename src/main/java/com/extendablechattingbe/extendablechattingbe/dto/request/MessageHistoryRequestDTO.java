package com.extendablechattingbe.extendablechattingbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "메세지 목록을 가져오는데 사용되는 DTO")
public class MessageHistoryRequestDTO {

    @Schema(description = "멤버의 아이디", example = "1")
    private Long memberId;

    private PageRequestDTO pageRequest;

}