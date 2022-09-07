package com.extendablechattingbe.extendablechattingbe.controller;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessage.*;

import java.util.List;

import com.extendablechattingbe.extendablechattingbe.common.dto.ApiResponse;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageHistoryRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponseDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Tag(name="rooms",description="채팅방 API")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms")
    public Long register(@RequestBody @Valid RoomRequest request) {
        return roomService.register(request);
    }

    @GetMapping("/rooms")
    public PageResponse getList(@RequestBody PageRequestDTO request) {
        return roomService.getList(request);
    }


    @GetMapping("/rooms/{roomId}")
    private RoomResponse getOne(@PathVariable Long roomId) {
        return roomService.getOne(roomId);
    }

    @DeleteMapping("/rooms/{roomId}")
    private void Delete(@PathVariable Long roomId) {
        roomService.delete(roomId);
    }

    @GetMapping("/rooms/{roomId}/chats")
    public ApiResponse<List<MessageResponseDTO>> getMessageHistory(@PathVariable Long roomId, @RequestBody MessageHistoryRequestDTO requestDTO) {
        Long memberId = requestDTO.getMemberId();
        PageRequestDTO pageRequest = requestDTO.getPageRequest();
        List<MessageResponseDTO> messageHistory = roomService.getMessageHistory(roomId, memberId, pageRequest);
        return ApiResponse.success(SUCCESS_NO_CONTENT, messageHistory);
    }
}
