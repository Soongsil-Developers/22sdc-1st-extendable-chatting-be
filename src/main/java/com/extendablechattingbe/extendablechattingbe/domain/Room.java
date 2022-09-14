package com.extendablechattingbe.extendablechattingbe.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String roomName;

//    @Transient   웹 소켓과 중복
//    private Set<WebSocketSession> sessions = new HashSet<>();

    public Room(String roomName) {
        this.roomName = roomName;
    }

//    웹 소켓과 중복
//    public void sendMessage(Message message, ObjectMapper objectMapper) {
//        sessions.parallelStream().forEach(session -> {
//            try {
//                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
}
