package com.extendablechattingbe.extendablechattingbe.dto.request;

import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequestDto {

    @Schema(description = "메세지 내용")
    private String message;

    @Schema(description = "메세지 보낸 멤버의 닉네임")
    private String nickname;

    @Schema(description = "메세지가 전송된 방의 아이디")
    private Long roomId;

    @Schema(description = "메세지를 보낸 멤버의 아이디")
    private Long memberId;


    @Schema(description = "메세지 타입")
    private MessageType type;

    @Builder
    public MessageRequestDto(String message, String nickname, Long roomId, Long memberId, MessageType type) {
        this.message = message;
        this.nickname = nickname;
        this.roomId = roomId;
        this.memberId = memberId;
        this.type = type;
    }
}
