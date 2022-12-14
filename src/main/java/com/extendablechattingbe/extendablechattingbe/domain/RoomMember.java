package com.extendablechattingbe.extendablechattingbe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"member", "room"})
@Getter
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"room_id", "member_id"}
        )
    }
)
public class RoomMember {

    @Id
    @GeneratedValue
    @Column(name = "room_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public RoomMember(Member member, Room room) {
        this.member = member;
        this.room = room;
    }
}
