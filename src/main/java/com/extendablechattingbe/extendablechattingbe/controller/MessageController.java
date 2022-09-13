package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponseDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


/**
 * 메세지 전송, 삭제, 읽어오기등을 담당하는 컨트롤러
 */

@Tag(name = "messages", description = "채팅 메시지 API")
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;


    @PostMapping("/rooms/{roomId}/chats")
    public ResponseEntity<Message> sendMessage(@PathVariable Long roomId,
        @RequestBody MessageRequestDTO messageRequest) {
        Message message = messageService.registerMessage(roomId, messageRequest);
        return ResponseEntity.created(URI.create("/rooms/" + roomId + "/chats/" + message.getId()))
            .body(message);

    }


    @GetMapping("/rooms/{roomId}/chats")

    public ResponseEntity<PageResponse> getMessageList(@PathVariable Long roomId,
        PageRequestDTO requestDTO) {
        PageResponse response = messageService.getMessageList(roomId, requestDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/rooms/{roomId}/chats/{chatId}")
    public ResponseEntity<MessageResponseDTO> getMessageOne(@PathVariable("roomId") Long roomId,
                                                            @PathVariable("chatId") Long chatId) {
        MessageResponseDTO response = messageService.getOne(chatId);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/rooms/{roomId}/chats/{chatId}")
    public ResponseEntity<Object> DeleteMessage(@PathVariable("roomId") Long roomId,
        @PathVariable("chatId") Long chatId) {
        messageService.DeleteMessage(chatId);
        return ResponseEntity.ok().build();
    }
}
