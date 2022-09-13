package com.extendablechattingbe.extendablechattingbe.dto.response;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class MessageResponseDTO {

    private final Long id;
    private final String message;
    private final MessageType type;
    private final Long memberId;
    private final Long roomId;
    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss",
        locale = "Asia/Seoul"
    )
    private final LocalDateTime createDate;

    public static MessageResponseDTO from(Message message) {
        return MessageResponseDTO.builder()
            .id(message.getId())
            .message(message.getMessage())
            .type(message.getType())
            .memberId(message.getMember().getId())
            .roomId(message.getRoom().getId())
            .createDate(message.getCreatedDate())
            .build();
    }
}