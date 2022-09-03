package com.extendablechattingbe.extendablechattingbe.controller;

import static com.extendablechattingbe.extendablechattingbe.error.ErrorCode.*;

import com.extendablechattingbe.extendablechattingbe.domain.Member;
import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.domain.Room;
import com.extendablechattingbe.extendablechattingbe.error.ErrorCode;
import com.extendablechattingbe.extendablechattingbe.error.exception.NotFoundException;
import com.extendablechattingbe.extendablechattingbe.repository.MemberRepository;
import com.extendablechattingbe.extendablechattingbe.repository.MessageRepository;
import com.extendablechattingbe.extendablechattingbe.repository.RoomRepository;
import com.extendablechattingbe.extendablechattingbe.security.token.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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


    @MessageMapping("/chat/message")
    public void message(Message message, @Header("token") String token) {

        Member member = getMemberFromToken(token);

        message.MessageWhereFrom(member, member.getNickname());

        messageRepository.save(message);

        if (message.getType().ENTER.equals(message.getType())) {
            message.EnterTypeMessage("[알림]", member.getNickname() + "님이 입장했습니다.");

        }

        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    private Member getMemberFromToken(String token) {
        String loginId = jwtTokenProvider.getUserNameFromJwt(token);

        Member member = memberRepository.findByLoginId(loginId)
            .orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND_ERROR));
        return member;
    }


}
