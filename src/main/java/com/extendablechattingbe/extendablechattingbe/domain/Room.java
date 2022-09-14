package com.extendablechattingbe.extendablechattingbe.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


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


    public Room(String roomName) {
        this.roomName = roomName;
    }
}

