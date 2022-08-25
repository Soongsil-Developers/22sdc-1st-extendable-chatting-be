package com.extendablechattingbe.extendablechattingbe.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Member {

    @Id @GeneratedValue(strategy = AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;


    @Builder
    public Member(String name) {
        this.name = name;
    }
    //
//   @OneToMany(mappedBy = "user",orphanRemoval = true)
//    private List<Message> messageList = new ArrayList<>();
//
//   @OneToMany(mappedBy = "user")
//    private List<RoomUser> roomUserList  = new ArrayList<>();
}
