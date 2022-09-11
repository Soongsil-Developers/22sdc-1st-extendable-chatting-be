package com.extendablechattingbe.extendablechattingbe.dto.request;

import java.time.LocalDateTime;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.*;

@Getter
@NoArgsConstructor
public class MessageRequestDTO {

    private String message;
    private MessageType type;
    private Long memberId;
    private Long roomId;

    @Builder
    public MessageRequestDTO(String message, MessageType type, Long memberId, Long roomId) {
        this.message = message;
        this.type = type;
        this.memberId = memberId;
        this.roomId = roomId;
    }
}