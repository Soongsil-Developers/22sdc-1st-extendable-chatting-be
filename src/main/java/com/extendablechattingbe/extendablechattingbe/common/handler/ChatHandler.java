package com.extendablechattingbe.extendablechattingbe.common.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        String payload = textMessage.getPayload();

        log.info("payload : " + payload);

//        TextMessage welcomeMessage = new TextMessage("Welcome");
//        session.sendMessage(welcomeMessage);
    }

}
