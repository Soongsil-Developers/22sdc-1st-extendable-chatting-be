package com.extendablechattingbe.extendablechattingbe.common;

import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private String TOPIC="kafka-chat";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info(String.format("[Kafka] Produce message(kafka-chat) : %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }
}