package com.extendablechattingbe.extendablechattingbe.dto.request;

import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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