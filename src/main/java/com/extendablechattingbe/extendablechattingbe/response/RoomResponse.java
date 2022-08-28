package com.extendablechattingbe.extendablechattingbe.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
@Schema(description="채팅방 생성 응답DTO")
@Getter
public class RoomResponse {
    @Schema(description="채팅방 아이디")
    private Long id;
    @Schema(description="채팅방 이름")
    private String roomName;

    @Builder
    public RoomResponse(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }
}
