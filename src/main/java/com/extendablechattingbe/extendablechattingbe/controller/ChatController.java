package com.extendablechattingbe.extendablechattingbe.controller;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.MessageRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import com.extendablechattingbe.extendablechattingbe.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;


    @MessageMapping("/chat/message")
    public void message(Message message,@Header("token")String token){

        String nickname = jwtTokenProvider.getUserNameFromJwt(token);

        Member member = memberRepository.findByLoginId(nickname)
            .orElseThrow(() -> new RuntimeException("없는 회원입니다."));

        message.addMember(member);
        message.addSender(member.getNickname());

        log.info("nickname={}",nickname);

        Room room = roomRepository.findById(message.getRoomId())
            .orElseThrow(() -> new RuntimeException("없는 방입니다."));

        message.addRoom(room);

        messageRepository.save(message);

        if(message.getType().ENTER.equals(message.getType())) {
            message.addSender("[알림]");
            message.addMessage(nickname + "님이 입장했습니다.");

        }

        messagingTemplate.convertAndSend("/sub/chat/room/"+message.getRoomId(),message);
    }


}
