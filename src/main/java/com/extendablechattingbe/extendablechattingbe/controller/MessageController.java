package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDto;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.service.MessageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 메세지 전송, 삭제, 읽어오기등을 담당하는 컨트롤러
 */

@Tag(name = "messages", description = "채팅 메시지 API")
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

//    웹 소켓으로 처리
//    @PostMapping("/rooms/{roomId}/chats")
//    public ResponseEntity<Message> sendmessage(@PathVariable Long roomId,
//        @RequestBody MessageRequestDto messageRequest) {
//        Message message = messageService.registermessage(roomId, messageRequest);
//        messageService.sendMessage(roomId,message);
//        return ResponseEntity.created(URI.create("/rooms/" + roomId + "/chats/" + message.getId()))
//            .body(message);
//
//    }


    @GetMapping("/rooms/{roomId}/chats")
    public ResponseEntity<PageResponse> getMessageList(@PathVariable Long roomId,
        PageRequestDTO requestDTO) {
        PageResponse response = messageService.getMessages(roomId, requestDTO);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/rooms/{roomId}/chats/{chatId}")
    public ResponseEntity<MessageResponse> getMessageOne(@PathVariable("roomId") Long roomId,
        @PathVariable("chatId") Long chatId) {
        MessageResponse response = messageService.getOne(chatId);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/rooms/{roomId}/chats/{chatId}")
    public ResponseEntity<Object> deleteMessage(@PathVariable("roomId") Long roomId,
        @PathVariable("chatId") Long chatId) {
        messageService.deleteMessage(chatId);
        return ResponseEntity.noContent().build();
    }
}
