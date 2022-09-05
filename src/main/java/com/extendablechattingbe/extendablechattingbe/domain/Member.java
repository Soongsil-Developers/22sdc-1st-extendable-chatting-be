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
    private String loginId;

    private String nickname;

    private String password;

    @Builder
    public Member(String loginId, String nickname, String password) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
    }
}
