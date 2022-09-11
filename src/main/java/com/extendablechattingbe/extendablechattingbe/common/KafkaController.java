package com.extendablechattingbe.extendablechattingbe.common;

import com.extendablechattingbe.extendablechattingbe.dto.request.MessageRequestDTO;
import com.extendablechattingbe.extendablechattingbe.dto.response.MessageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducer kafkaProducer;

    @PostMapping(value = "/test")
    public void TestSendMessage(@RequestBody(required = true) String message) {
        this.kafkaProducer.sendMessage(message);
    }
}