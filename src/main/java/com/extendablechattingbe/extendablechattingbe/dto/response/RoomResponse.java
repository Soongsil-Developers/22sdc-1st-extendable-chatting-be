package com.extendablechattingbe.extendablechattingbe.dto.response;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomResponse {

    private Long id;
    private String roomName;

    @Builder
    public RoomResponse(Long id, String roomName) {
        this.id = id;
        this.roomName = roomName;
    }

    public static RoomResponse from(Room room){
        return RoomResponse.builder()
            .id(room.getId())
            .roomName(room.getRoomName())
            .build();
    }
}
