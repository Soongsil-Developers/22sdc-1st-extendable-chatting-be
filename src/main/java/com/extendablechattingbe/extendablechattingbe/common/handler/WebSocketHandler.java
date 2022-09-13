package com.extendablechattingbe.extendablechattingbe.common.handler;

import com.extendablechattingbe.extendablechattingbe.common.exception.CustomException;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.extendablechattingbe.extendablechattingbe.common.ResponseMessages.MESSAGE_BAD_REQUEST_ERROR;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {

    private static final HashMap<Long, Set<WebSocketSession>> chatRoomMap = new HashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //TODO -> 예외들 커스텀으로 변경해야 함

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        //TODO -> body에 넣어서 보낼 수 있음 . session.getAttributes()
        //ws://localhost:8080/ws/chat?roomId=1 이렇게 하니까 일단 되긴함.
        String json = qs2json(URLDecoder.decode(session.getUri().getQuery(), StandardCharsets.UTF_8));

        Map<String, String> map = objectMapper.readValue(json, Map.class);

        Long roomId = Long.parseLong(map.get("roomId"));
        String sender = map.get("memberId");

        chatRoomMap.computeIfAbsent(roomId, k -> new HashSet<>());
        chatRoomMap.get(roomId).add(session);

        log.info("[CONNECT] user successfully connected");

    }

    @Override
    //TODO -> 이 로직들에 @Transactional 붙일 수 있을까?? (일부 세션에만 전파될 수도 있으니까)
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException{
        String payload = textMessage.getPayload();
        log.info("payload : " + payload);

        MessageRequestDTO messageRequestDTO = objectMapper.readValue(payload, MessageRequestDTO.class);

        sendMessage(messageRequestDTO);

        //TODO -> DB에 넣어주는 로직 작성해야함.
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {

        final String json = qs2json(URLDecoder.decode(session.getUri().getQuery(), StandardCharsets.UTF_8));

        Map<String,String> map = objectMapper.readValue(json, Map.class);

        Long roomId = Long.parseLong(map.get("roomId"));
        String sender = map.get("memberId");
        chatRoomMap.get(roomId).remove(session);

        log.info("[DISCONNECT] user disconnect success");
        for (WebSocketSession ws : chatRoomMap.get(roomId)) { //방에 남아있는 사람에게 GOOD BYE
            ws.sendMessage(new TextMessage(sender+"님이 채팅방을 나갔습니다."));
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

    public void sendMessage(MessageRequestDTO message) {
        Gson gson = new Gson();
        for (WebSocketSession ws : chatRoomMap.get(message.getRoomId())) {
            try {
                ws.sendMessage(new TextMessage(gson.toJson(message)));
            } catch (IOException e) {
                throw new CustomException(MESSAGE_BAD_REQUEST_ERROR);
            }
        }
    }
}
