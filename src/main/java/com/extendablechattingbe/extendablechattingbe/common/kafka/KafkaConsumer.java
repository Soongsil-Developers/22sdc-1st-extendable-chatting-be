package com.extendablechattingbe.extendablechattingbe.common.kafka;

import com.extendablechattingbe.extendablechattingbe.common.handler.WebSocketHandler;
import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponseDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaConsumer {
    @Autowired
    private WebSocketHandler webSocketHandler;


    @KafkaListener(topics = "kafka-chat", groupId = "kafka-one")
    public void consume(MessageRequestDTO message) {
        log.info(String.format("[Kafka] Consumed message(kafka-chat) : %s", message));
        webSocketHandler.sendMessage(message);
    }

}