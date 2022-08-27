package com.extendablechattingbe.extendablechattingbe.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String nickname;

    @Transient
    private String jwtToken;

    @Builder
    public Member(String nickname,String jwtToken) {
        this.nickname = nickname;
        this.jwtToken = jwtToken;
    }
    //
//   @OneToMany(mappedBy = "user",orphanRemoval = true)
//    private List<Message> messageList = new ArrayList<>();
//
//   @OneToMany(mappedBy = "user")
//    private List<RoomUser> roomUserList  = new ArrayList<>();
}
