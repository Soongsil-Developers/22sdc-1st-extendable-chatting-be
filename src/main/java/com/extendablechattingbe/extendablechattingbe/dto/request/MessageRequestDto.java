package com.extendablechattingbe.extendablechattingbe.dto.request;

import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequestDto {

    private String message;
    private String nickname;
    private Long roomId;
    private MessageType type;

    @Builder
    public MessageRequestDto(String message, String nickname, Long roomId, MessageType type) {
        this.message = message;
        this.nickname = nickname;
        this.roomId = roomId;
        this.type = type;
    }
}