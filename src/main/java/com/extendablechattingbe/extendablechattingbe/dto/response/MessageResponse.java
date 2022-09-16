package com.extendablechattingbe.extendablechattingbe.dto.response;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "메세지 반환시 사용되는 DTO")
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

    @JsonFormat(
        shape = JsonFormat.Shape.STRING,
        pattern = "yyyy-MM-dd HH:mm:ss",
        locale = "Asia/Seoul"
    )
    @Schema(description = "메세지가 생성된 시간")
    private final LocalDateTime createDate;

    public static MessageResponse from(Message message) {
        return MessageResponse.builder()
            .id(message.getId())
            .message(message.getMessage())
            .type(message.getType())
            .memberId(message.getMember().getId())
            .roomId(message.getRoom().getId())
            .createDate(message.getCreatedDate())
            .build();
    }
}
