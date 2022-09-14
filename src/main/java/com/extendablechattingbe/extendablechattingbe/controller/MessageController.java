package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponseDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * 메세지 전송, 삭제, 읽어오기등을 담당하는 컨트롤러
 */

@Tag(name = "messages", description = "채팅 메시지 API")
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @GetMapping("/rooms/{roomId}/chats")
    public ResponseEntity<PageResponse> getMessageList(@PathVariable Long roomId,
        PageRequestDTO requestDTO) {
        PageResponse response = messageService.getMessagelist(roomId, requestDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/rooms/{roomId}/chats/{chatId}")
    public ResponseEntity<MessageResponseDTO> getMessageOne(@PathVariable("roomId") Long roomId,
                                                            @PathVariable("chatId") Long chatId) {
        MessageResponseDTO response = messageService.getOne(chatId);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/rooms/{roomId}/chats/{chatId}")
    public ResponseEntity<Object> deleteMessage(@PathVariable("roomId") Long roomId,
        @PathVariable("chatId") Long chatId) {
        messageService.deleteMessage(chatId);

        return ResponseEntity.noContent().build();

    }
}
