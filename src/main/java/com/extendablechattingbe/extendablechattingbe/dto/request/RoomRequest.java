package com.extendablechattingbe.extendablechattingbe.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RoomRequest {


    @NotBlank
    private String roomName;

    public RoomRequest(String roomName) {
        this.roomName = roomName;
    }
}
