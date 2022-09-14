package com.extendablechattingbe.extendablechattingbe.common.handler;

import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.service.MemberService;
import com.extendablechattingbe.extendablechattingbe.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.extendablechattingbe.extendablechattingbe.common.CustomMessages.MESSAGE_BAD_REQUEST_ERROR;
import static com.extendablechattingbe.extendablechattingbe.common.CustomMessages.ROOM_NOT_FOUND_ERROR;
import static com.extendablechattingbe.extendablechattingbe.domain.MessageType.ENTER;
import static com.extendablechattingbe.extendablechattingbe.domain.MessageType.EXIT;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private static final HashMap<Long, Set<WebSocketSession>> chatRoomMap = new HashMap<>();//방 초대된 순간 ~ 방 아예 나가버린 순간,
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageService messageService;
    private final MemberService memberService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session){/* sending messages over a WebSocket connection */
        //TODO -> body에 넣어서 보낼 수 있음 . session.getAttributes()
        String json = qs2json(URLDecoder.decode(Objects.requireNonNull(session.getUri()).getQuery(), StandardCharsets.UTF_8));

        try {
            Map<String, String> map = objectMapper.readValue(json, Map.class);
            Long roomId = Long.parseLong(map.get("roomId"));
            Long memberId=Long.parseLong(map.get("memberId"));
            String nickname=memberService.getMemberOne(memberId).getNickname();
            // 웹소켓 세션 추가
            chatRoomMap.computeIfAbsent(roomId, k -> new HashSet<>());
            chatRoomMap.get(roomId).add(session);

            //log.info("[CONNECT] user successfully connected");
            MessageRequestDTO enterMsg=MessageRequestDTO.builder()
                .message(nickname+"님이 들어왔습니다.")
                .type(ENTER)
                    .memberId(memberId).roomId(roomId).build();
            sendSocketMessage(enterMsg);

        } catch (IOException e) {
            //log.error(e.getMessage());
        }

    }

    @Override
    //TODO -> 이 로직들에 @Transactional 붙일 수 있을까?? (일부 세션에만 전파될 수도 있으니까)
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage){
        final String payload = textMessage.getPayload();
        //log.info("payload : " + payload);
        try {
            MessageRequestDTO messageRequestDTO = objectMapper.readValue(payload, MessageRequestDTO.class);
            messageService.saveMessage(messageRequestDTO);
            sendSocketMessage(messageRequestDTO);

        } catch (IOException e) {
            //log.error(e.getMessage());
        }

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){

        final String json = qs2json(URLDecoder.decode(session.getUri().getQuery(), StandardCharsets.UTF_8));

        try {
            MessageRequestDTO msgDTO = objectMapper.readValue(json, MessageRequestDTO.class);
            Long roomId = msgDTO.getRoomId();
            Long memberId=msgDTO.getMemberId();
            String nickname=memberService.getMemberOne(memberId).getNickname();
            chatRoomMap.get(roomId).remove(session);
            MessageRequestDTO exitMsg=MessageRequestDTO.builder()
                .message(nickname+"님이 나갔습니다.")
                .type(EXIT)
                .memberId(memberId).roomId(roomId).build();
            sendSocketMessage(exitMsg);
        } catch (JsonProcessingException e) {
            throw new CustomException(MESSAGE_BAD_REQUEST_ERROR);
        }


        //log.info("[DISCONNECT] user disconnect success");
    }

    private String qs2json(String a) {
        StringBuilder res = new StringBuilder("{\"");

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '=') {
                res.append("\"" + ":" + "\"");
            } else if (a.charAt(i) == '&') {
                res.append("\"" + "," + "\"");
            } else {
                res.append(a.charAt(i));
            }
        }
        res.append("\"" + "}");
        return res.toString();
    }


    public void sendSocketMessage(MessageRequestDTO message) {
        Set<WebSocketSession> sessions= chatRoomMap.get(message.getRoomId());
        if(sessions==null){
            throw new CustomException(ROOM_NOT_FOUND_ERROR);
        }
        sessions.parallelStream().forEach(session -> messageService.sendMessage(session,message));
    }
}
