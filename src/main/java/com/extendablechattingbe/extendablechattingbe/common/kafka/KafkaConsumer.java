package com.extendablechattingbe.extendablechattingbe.common.kafka;

import com.extendablechattingbe.extendablechattingbe.common.handler.WebSocketHandler;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaConsumer {
    @Autowired
    private WebSocketHandler webSocketHandler;

    // 1) 구독 URL 설정
    // 2) 구독 URL으로 message를 보내야 함 convertAndSend
    @KafkaListener(topics = "kafka-chat", groupId = "kafka-one")
    public void consume(MessageRequestDTO message) {
        log.info(String.format("[Kafka] Consumed message(kafka-chat) : %s", message));
        webSocketHandler.sendSocketMessage(message);
    }

}