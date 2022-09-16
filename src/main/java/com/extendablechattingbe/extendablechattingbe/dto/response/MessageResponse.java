package com.extendablechattingbe.extendablechattingbe.dto.response;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Schema(description = "메세지 반환시 사용되는 DTO")
@Getter
@ToString
public class MessageResponse {


    @Schema(description = "메세지 아이디", example = "1")
    private Long id;

    @Schema(description = "메세지 내용", defaultValue = "메세지 내용")
    private String message;

    @Schema(description = "메세지가 전송된 방의 아이디", example = "2")
    private Long roomId;

    @Schema(description = "메세지를 보낸 멤버의 아이디", example = "3")
    private Long memberId;

    @Schema(description = "메세지의 타입", example = "TALK")
    private MessageType type;

    @Builder
    public MessageResponse(Long id, String message, Long roomId, Long memberId,
        MessageType type) {
        this.id = id;
        this.message = message;
        this.roomId = roomId;
        this.memberId = memberId;
        this.type = type;
    }


    public static MessageResponse from(Message message) {
        return MessageResponse.builder()
            .id(message.getId())
            .message(message.getMessage())
            .roomId(message.getRoom().getId())
            .memberId(message.getMember().getId())
            .type(message.getType())
            .build();
    }
}
