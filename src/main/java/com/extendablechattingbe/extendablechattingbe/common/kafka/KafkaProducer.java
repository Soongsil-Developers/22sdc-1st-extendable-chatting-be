package com.extendablechattingbe.extendablechattingbe.common.kafka;

import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private String TOPIC="kafka-chat";

    private final KafkaTemplate<String, MessageRequestDto> kafkaTemplate;

    public void sendMessage(MessageRequestDto message) {
        //log.info(String.format("[Kafka] Produce message(kafka-chat) : %s", message));
        this.kafkaTemplate.send(TOPIC, message);
    }
}