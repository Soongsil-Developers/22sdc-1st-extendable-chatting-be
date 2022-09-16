package com.extendablechattingbe.extendablechattingbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RoomMemberResponse {

    @Schema(description = "방에 속한 멤버를 나타내는 객체의 아이디")
    private Long id;

    @Schema(description = "방에 속한 멤버를 나타내는 객체의 멤버의 아이디")
    private Long memberId;

    @Schema(description = "방에 속한 멤버를 나타내는 객체의 방의 아이디")
    private Long roomId;

    public RoomMemberResponse(Long id, Long memberId, Long roomId) {
        this.id = id;
        this.memberId = memberId;
        this.roomId = roomId;
    }
}
