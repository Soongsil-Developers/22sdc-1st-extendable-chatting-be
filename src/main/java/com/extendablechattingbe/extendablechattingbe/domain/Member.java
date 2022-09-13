package com.extendablechattingbe.extendablechattingbe.domain;

import java.util.HashSet;
import java.util.Set;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "member_id")
    private Long id;


    @Column(unique = true)
    private String nickname;


    public Member(String nickname) {
        this.nickname = nickname;
    }
}
