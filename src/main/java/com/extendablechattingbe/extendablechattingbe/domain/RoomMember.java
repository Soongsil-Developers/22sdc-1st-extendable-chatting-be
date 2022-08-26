package com.extendablechattingbe.extendablechattingbe.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class RoomMember {

    @Id
    @GeneratedValue
    @Column(name = "roomuser_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public RoomMember(Member member, Room room) {
        this.member = member;
        this.room = room;
    }
}
