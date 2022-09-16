package com.extendablechattingbe.extendablechattingbe.dto.response;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "방 반환시 사용되는 DTO")
@Getter
public class RoomResponse {

    @Schema(description = "방의 아이디", example = "1")
    private Long id;

    @Schema(description = "방의 이름", example = "방 이름")
    private String roomName;

    @Builder
    public RoomResponse(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public static RoomResponse from(Room room) {
        return RoomResponse.builder()
            .id(room.getId())
            .roomName(room.getRoomName())
            .build();
    }
}
