package com.extendablechattingbe.extendablechattingbe.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TestProducer {

    private String TOPIC="kafka-chat";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        log.info(String.format("Produce message(test) : %s", message));
        log.info(kafkaTemplate);
        this.kafkaTemplate.send(TOPIC, message);
    }
}