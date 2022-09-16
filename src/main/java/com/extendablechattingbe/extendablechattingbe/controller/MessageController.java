package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.common.response.SimpleResponseDTO;
import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDto;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
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


    @Operation(summary = "특정 방에 있는 메세지 목록 획득", description = "특정 방 아이디를 입력하면 특정 방에 있는 메세지를 페이징 처리해서 얻을 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 방에 있는 메세지 목록을 성공적으로 획득", content = @Content(schema = @Schema(implementation = PageResponse.class))),
        @ApiResponse(responseCode = "404", description = "입력한 정보가 데이터 베이스에 저장되어있지 않습니다.", content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    @GetMapping("/rooms/{roomId}/chats")
    public ResponseEntity<PageResponse> getMessageList(@PathVariable Long roomId,
        @ParameterObject PageRequestDTO requestDTO) {
        PageResponse response = messageService.getMessages(roomId, requestDTO);
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "특정 메세지 획득", description = "특정 채팅에 대한 방 아이디 및 채팅 아이디를 파라미터로 입력하면, 특정 채팅을 가져올 수 있습니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "특정 메세지를 성공적으로 획득", content = @Content(schema = @Schema(implementation = MessageResponse.class))),
        @ApiResponse(responseCode = "404", description = "입력한 정보가 데이터 베이스에 저장되어있지 않습니다.", content = @Content(schema = @Schema(implementation = SimpleResponseDTO.class)))
    })
    @GetMapping("/rooms/{roomId}/chats/{chatId}")
    public ResponseEntity<MessageResponse> getMessageOne(
        @Parameter(name = "roomId", description = "방 의 아이디", in = ParameterIn.PATH) @PathVariable("roomId") Long roomId,
        @Parameter(name = "chatId", description = "메세지의 아이디", in = ParameterIn.PATH) @PathVariable("chatId") Long chatId) {
        MessageResponse response = messageService.getOne(chatId);
        return ResponseEntity.ok().body(response);
    }


    @DeleteMapping("/rooms/{roomId}/chats/{chatId}")
    public ResponseEntity<Object> deleteMessage(
        @Parameter(name = "roomId", description = "방 의 아이디", in = ParameterIn.PATH) @PathVariable("roomId") Long roomId,
        @Parameter(name = "chatId", description = "메세지의 아이디", in = ParameterIn.PATH) @PathVariable("chatId") Long chatId) {
        messageService.deleteMessage(chatId);
        return ResponseEntity.noContent().build();
    }
}
