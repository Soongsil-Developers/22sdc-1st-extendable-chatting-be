package com.extendablechattingbe.extendablechattingbe.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String roomName;

    public Room(String roomName) {
        this.roomName = roomName;
    }




}
