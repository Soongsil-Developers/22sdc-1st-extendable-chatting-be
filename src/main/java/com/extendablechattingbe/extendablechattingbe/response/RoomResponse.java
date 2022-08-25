package com.extendablechattingbe.extendablechattingbe.response;

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
}
