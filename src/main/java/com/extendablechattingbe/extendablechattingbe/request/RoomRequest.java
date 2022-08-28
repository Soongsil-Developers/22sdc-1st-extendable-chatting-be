package com.extendablechattingbe.extendablechattingbe.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
@Schema(description="채팅방 생성 요청DTO")
@Getter
public class RoomRequest {

    @Schema(description="채팅방 이름", required=true)
    @NotBlank
    private String roomName;

    public RoomRequest(String roomName) {
        this.roomName = roomName;
    }
}
