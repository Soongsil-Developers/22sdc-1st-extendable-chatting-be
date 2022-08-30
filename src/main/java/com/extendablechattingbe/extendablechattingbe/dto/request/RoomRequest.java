package com.extendablechattingbe.extendablechattingbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Schema(description="채팅방 생성 요청DTO")
@Getter
@NoArgsConstructor
public class RoomRequest {

    @Schema(description="채팅방 이름", required=true)
    @NotBlank
    private String roomName;

    @Builder
    public RoomRequest(String roomName) {
        this.roomName = roomName;
    }
}
