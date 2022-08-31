package com.extendablechattingbe.extendablechattingbe.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @CreatedDate
    private LocalDateTime createdDate;


    private Long roomId;

    private String sender;

    @Builder
    public Message(String message, Member member, Room room, MessageType type) {
        this.message = message;
        this.member = member;
        this.room = room;
        this.type = type;
    }

    public void addMember(Member member){
        this.member = member;
    }

    public void addRoom(Room room){
        this.room = room;
    }

    public void addSender(String nickname){
        this.sender = nickname;
    }

    public void addMessage(String message){
        this.message = message;
    }

}
