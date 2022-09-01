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


    @Enumerated(EnumType.STRING)
    private MessageType type;

    @CreatedDate
    private LocalDateTime createdDate;


    private Long roomId;

    private String sender;


    @Builder
    public Message(String message, Member member,
        MessageType type, Long roomId, String sender) {
        this.message = message;
        this.member = member;
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
    }


    public void MessageWhereFrom(Member member, String sender) {
        this.member = member;
        this.sender = sender;
    }

    public void EnterTypeMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

}
