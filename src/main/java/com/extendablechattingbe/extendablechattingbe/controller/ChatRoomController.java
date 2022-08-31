package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import com.extendablechattingbe.extendablechattingbe.security.dto.LoginInfo;
import com.extendablechattingbe.extendablechattingbe.security.token.JwtTokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {


    private final RoomRepository roomRepository;
    private final JwtTokenProvider jwtTokenProvider;


    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> room() {
        return roomRepository.findAll();
    }

    @PostMapping("/room")
    @ResponseBody
    public Room createRoom(@RequestParam String name) {
        Room room = Room.builder()
            .roomName(name)
            .build();

        roomRepository.save(room);
        return room;
    }

    //OAuth
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable Long roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Room roomInfo(@PathVariable Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException());
    }

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
    }


}
