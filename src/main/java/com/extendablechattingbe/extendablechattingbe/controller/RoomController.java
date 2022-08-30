package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.dto.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.dto.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.dto.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms")
    public Long register(@Valid RoomRequest request) {
        return roomService.register(request);
    }

    @GetMapping("/rooms")
    public PageResponse getList(PageRequestDTO request) {
        return roomService.getList(request);
    }


    @GetMapping("/rooms/{roomId}")
    private RoomResponse getOne(@PathVariable Long roomId) {
        return roomService.getOne(roomId);
    }

    @DeleteMapping("/rooms/{roodId}")
    private void Delete(@PathVariable Long roomId) {
        roomService.delete(roomId);
    }


}
