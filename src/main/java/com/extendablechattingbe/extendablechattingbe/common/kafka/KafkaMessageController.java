package com.extendablechattingbe.extendablechattingbe.common.kafka;

import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaMessageController {

    private final KafkaProducer kafkaProducer;

    @PostMapping(value = "/chat")
    public void TestSendMessage(@RequestBody(required = true) MessageRequestDTO message) {
        this.kafkaProducer.sendMessage(message);
    }
}