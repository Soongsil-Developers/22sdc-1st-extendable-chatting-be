package com.extendablechattingbe.extendablechattingbe.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String roomName;

    @Builder
    public Room(String roomName){
        this.roomName = roomName;
    }


//    @OneToMany(mappedBy = "room")
//    private List<Message> messageList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "room")
//    private List<RoomUser> roomUserList = new ArrayList<>();






}
