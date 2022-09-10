package com.extendablechattingbe.extendablechattingbe.dto.response;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageResponse {
    private Long id;

    private String message;

    private Long roomId;

    private Long memberId;

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




    public static MessageResponse from(Message message){
        return MessageResponse.builder()
            .id(message.getId())
            .message(message.getMessage())
            .roomId(message.getRoom().getId())
            .memberId(message.getMember().getId())
            .type(message.getType())
            .build();
    }
}
