package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.request.PageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.request.RoomRequest;
import com.extendablechattingbe.extendablechattingbe.response.PageResponse;
import com.extendablechattingbe.extendablechattingbe.response.RoomResponse;
import com.extendablechattingbe.extendablechattingbe.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    @PostMapping("/rooms")
    public Long register(@Valid @RequestBody RoomRequest request){
        return service.register(request);
    }

    @GetMapping("/rooms")
    public PageResponse getList(PageRequestDTO request){
        return service.getList(request);
    }


   @GetMapping("/rooms/{roomId}")
    private RoomResponse getOne(@PathVariable Long roomId){
        return service.getOne(roomId);
   }

   @DeleteMapping("/rooms/{roodId}")
    private void Delete(@PathVariable Long roomId){
        service.delete(roomId);
   }



}
