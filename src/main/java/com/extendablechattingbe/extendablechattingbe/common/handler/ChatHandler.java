package com.extendablechattingbe.extendablechattingbe.common.handler;

import com.extendablechattingbe.extendablechattingbe.domain.MessageType;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDto;
import com.extendablechattingbe.extendablechattingbe.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private static final HashMap<Long, Set<WebSocketSession>> chatRoomMap = new HashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("========afterConnectionEstablished========");

        String json = qs2json(URLDecoder.decode(Objects.requireNonNull(session.getUri()).getQuery(), StandardCharsets.UTF_8));

        try {
            Map<String, String> map = objectMapper.readValue(json, Map.class);
            Long roomId = Long.parseLong(map.get("roomId"));
            String userId = map.get("userId");

            chatRoomMap.computeIfAbsent(roomId, k -> new HashSet<>());
            chatRoomMap.get(roomId).add(session);

            //log.info("[CONNECT] user successfully connected");
            for (WebSocketSession ws : chatRoomMap.get(roomId)) {
                ws.sendMessage(new TextMessage(userId+"님이 채팅방에 입장하셨습니다."));
            }

        } catch (IOException e) {
            //log.error(e.getMessage());
        }
    }

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        String payload = textMessage.getPayload();
        //log.info("\npayload : " + payload);

        try {
            MessageRequestDto messageRequestDTO = objectMapper.readValue(payload, MessageRequestDto.class);
            Long roomId = messageRequestDTO.getRoomId();

            messageService.saveMessage(messageRequestDTO);

            if (messageRequestDTO.getType().equals(MessageType.TALK)) {
                for (WebSocketSession ws : chatRoomMap.get(roomId)) {
                    ws.sendMessage(new TextMessage(payload));
                }
            }

        } catch (IOException e) {
            //log.error(e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("========afterConnectionClosed========");

        String json = qs2json(URLDecoder.decode(session.getUri().getQuery(), StandardCharsets.UTF_8));

        try {
            Map<String, String> map = objectMapper.readValue(json, Map.class);
            Long roomId = Long.parseLong(map.get("roomId"));
            String userId = map.get("userId");

            chatRoomMap.get(roomId).remove(session);

            //log.info("[DISCONNECT] user disconnect success");
            for (WebSocketSession ws : chatRoomMap.get(roomId)) { //방에 남아있는 사람에게 GOOD BYE
                ws.sendMessage(new TextMessage(userId+"로그아웃하셨습니다."));
            }

        } catch (IOException e) {
            //log.error(e.getMessage());
        }
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

    private static JSONObject jsonToObjectParser(String jsonStr) {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
