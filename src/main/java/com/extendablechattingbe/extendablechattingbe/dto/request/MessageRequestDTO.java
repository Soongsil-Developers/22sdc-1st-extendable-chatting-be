package com.extendablechattingbe.extendablechattingbe.dto.request;

import java.io.Serializable;
import com.extendablechattingbe.extendablechattingbe.domain.MessageType;

import lombok.*;

@Getter
@NoArgsConstructor
public class MessageRequestDTO implements Serializable {

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

    @Override
    public String toString() {
        return "MessageRequestDTO{" +
            "message='" + message + '\'' +
            ", type=" + type +
            ", memberId=" + memberId +
            ", roomId=" + roomId +
            '}';
    }
}