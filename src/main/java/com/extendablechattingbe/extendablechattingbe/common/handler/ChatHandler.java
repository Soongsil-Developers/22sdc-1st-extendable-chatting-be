package com.extendablechattingbe.extendablechattingbe.common.handler;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDto;
import com.extendablechattingbe.extendablechattingbe.service.MessageService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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

@Component
@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private static final HashMap<Long, Set<WebSocketSession>> chatRoomMap = new HashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String json = qs2json(URLDecoder.decode(Objects.requireNonNull(session.getUri()).getQuery(), StandardCharsets.UTF_8));

        try {
            Map<String, String> map = objectMapper.readValue(json, Map.class);
            Long roomId = Long.parseLong(map.get("roomId"));
            String sender = map.get("sender");

            chatRoomMap.computeIfAbsent(roomId, k -> new HashSet<>());
            chatRoomMap.get(roomId).add(session);

            log.info("user connect success");
            session.sendMessage(new TextMessage("hello" + sender));

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    @Transactional
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        String payload = textMessage.getPayload();
        log.info("\npayload : " + payload);

        try {
            MessageRequestDto messageRequestDto = objectMapper.readValue(payload, MessageRequestDto.class);
            messageService.saveMessage(messageRequestDto);

            Long roomId = messageRequestDto.getRoomId();
            for (WebSocketSession ws : chatRoomMap.get(roomId)) {
                ws.sendMessage(new TextMessage(payload));
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String json = qs2json(URLDecoder.decode(session.getUri().getQuery(), StandardCharsets.UTF_8));

        try {
            Map<String, String> map = objectMapper.readValue(json, Map.class);
            Long roomId = Long.parseLong(map.get("roomId"));
            String sender = map.get("userId");

            chatRoomMap.get(roomId).remove(session);

            log.info("user disconnect success");
            session.sendMessage(new TextMessage("good bye" + sender));

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        //TODO -> 웹소켓 커넥션 끊을 때마다 IllegalStateException 발생하는데 어떻게 없애지?
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
