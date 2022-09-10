package com.extendablechattingbe.extendablechattingbe.common.handler;

import com.extendablechattingbe.extendablechattingbe.domain.Message;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDto;
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
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private static final HashMap<Long, Set<WebSocketSession>> chatRoomMap = new HashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //TODO -> 예외들 커스텀으로 변경해야 함

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        //TODO -> uri를 어떻게 보내야 이 코드가 잘 동작하는지 테스트(아마 querystring으로 보내야 되는 듯)
        //ws://localhost:8080/ws/chat?roomId=1 이렇게 하니까 일단 되긴함.
        String json = qs2json(URLDecoder.decode(session.getUri().getQuery(), StandardCharsets.UTF_8));

        Map<String, String> map = objectMapper.readValue(json, Map.class);

        Long roomId = Long.parseLong(map.get("roomId"));
        String sender = map.get("userId");

        chatRoomMap.computeIfAbsent(roomId, k -> new HashSet<>());
        chatRoomMap.get(roomId).add(session);

        log.info("user connect success");
        session.sendMessage(new TextMessage("hello" + sender));
    }

    @Override
    //TODO -> 이 로직들에 @Transactional 붙일 수 있을까?? (일부 세션에만 전파될 수도 있으니까)
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException, ParseException {
        String payload = textMessage.getPayload();
        log.info("payload : " + payload);

        MessageRequestDto messageRequestDto = objectMapper.readValue(payload, MessageRequestDto.class);

        Long roomId = messageRequestDto.getRoomId();

        if (!chatRoomMap.get(roomId).isEmpty()) {
            JSONObject jsonObj = jsonToObjectParser(payload);

            //일단 이렇게 세션에 sendMessage 하면 채팅이 가는지 확인 -> 포스트맨으로 확인 완료!
            //TODO -> 원하는 세션에만 전달되는지 확인
            //TODO -> 굳이 payload를 json으로 파싱했다가 다시 jsonString으로 파싱할 필요가 있나 확인

            //TODO -> https://supawer0728.github.io/2018/03/30/spring-websocket/ 여기 글 보면 다듬을 수 있을지도?
            // https://daddyprogrammer.org/post/4077/spring-websocket-chatting/
            for (WebSocketSession ws : chatRoomMap.get(roomId)) {
                ws.sendMessage(new TextMessage(jsonObj.toJSONString()));
            }
        }

        //TODO -> DB에 넣어주는 로직 작성해야함.
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws IOException {

        final String json = qs2json(URLDecoder.decode(session.getUri().getQuery(), StandardCharsets.UTF_8));

        Map<String,String> map = objectMapper.readValue(json, Map.class);

        Long roomId = Long.parseLong(map.get("roomId"));
        String sender = map.get("userId");

        chatRoomMap.get(roomId).remove(session);

        log.info("user disconnect success");
        session.sendMessage(new TextMessage("good bye" + sender));

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
