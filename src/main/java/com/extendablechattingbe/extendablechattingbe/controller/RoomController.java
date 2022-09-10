package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageHistoryRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponseDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Tag(name="rooms",description="채팅방 API")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<Room> register(@RequestBody @Valid RoomRequest request) {
        Room room=roomService.register(request);
        return ResponseEntity.created(URI.create("/rooms/"+room.getId())).body(room);
    }

    @GetMapping("/rooms")
    public ResponseEntity<PageResponse> getList(@RequestBody PageRequestDTO request) {
        return ResponseEntity.ok().body(roomService.getList(request));
    }


    @GetMapping("/rooms/{roomId}")
    private ResponseEntity<RoomResponse> getOne(@PathVariable Long roomId) {
        return ResponseEntity.ok().body(roomService.getOne(roomId));
    }

    @DeleteMapping("/rooms/{roomId}")
    private ResponseEntity delete(@PathVariable Long roomId) {
        roomService.delete(roomId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/rooms/{roomId}/chats")
    public ResponseEntity getMessageHistory(@PathVariable Long roomId, @RequestBody MessageHistoryRequestDTO requestDTO) {
        Long memberId = requestDTO.getMemberId();
        PageRequestDTO pageRequest = requestDTO.getPageRequest();
        List<MessageResponseDTO> messageHistory = roomService.getMessageHistory(roomId, memberId, pageRequest);
        return ResponseEntity.ok().body(messageHistory);
    }
}
