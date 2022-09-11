package com.extendablechattingbe.extendablechattingbe.common;

import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "kafka-chat", groupId = "kafka-one")
    public void consume(MessageResponse message) {
        log.info(String.format("[Kafka] Consumed message(kafka-chat) : %s", message));
    }

}