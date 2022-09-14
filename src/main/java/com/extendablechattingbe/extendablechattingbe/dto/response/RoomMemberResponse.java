package com.extendablechattingbe.extendablechattingbe.dto.response;

import lombok.Getter;

@Getter
public class RoomMemberResponse {
    private Long id;

    private Long memberId;

    private Long roomId;

    public RoomMemberResponse(Long id, Long memberId, Long roomId) {
        this.id = id;
        this.memberId = memberId;
        this.roomId = roomId;
    }
}
