package com.extendablechattingbe.extendablechattingbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "특정 방에 특정 멤버가 있음을 보여주는 DTO")
@Getter
public class RoomMemberResponse {

    @Schema(description = "방에 속한 멤버를 나타내는 객체의 아이디", example = "1")
    private Long id;

    @Schema(description = "방에 속한 멤버를 나타내는 객체의 멤버의 아이디", example = "3")
    private Long memberId;

    @Schema(description = "방에 속한 멤버를 나타내는 객체의 방의 아이디", example = "2")
    private Long roomId;

    public RoomMemberResponse(Long id, Long memberId, Long roomId) {
        this.id = id;
        this.memberId = memberId;
        this.roomId = roomId;
    }
}
