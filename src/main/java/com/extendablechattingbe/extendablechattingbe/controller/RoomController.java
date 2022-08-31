package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.service.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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


}
