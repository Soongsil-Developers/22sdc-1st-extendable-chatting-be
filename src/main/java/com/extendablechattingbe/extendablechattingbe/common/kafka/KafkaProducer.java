package com.extendablechattingbe.extendablechattingbe.common.kafka;

import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private String TOPIC="kafka-chat";

    private final KafkaTemplate<String, MessageRequestDTO> kafkaTemplate;

    public void sendMessage(MessageRequestDTO message) {
        log.info(String.format("[Kafka] Produce message(kafka-chat) : %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }
}