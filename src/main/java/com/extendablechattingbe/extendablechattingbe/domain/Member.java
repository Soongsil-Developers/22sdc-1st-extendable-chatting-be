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

    private String name;


    private String loginId;

    private String nickname;

    private String password;

    private boolean fromSocial;

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<MemberRole> roleSet = new HashSet<>();


    @Builder
    public Member(String loginId, String nickname, String password, boolean fromSocial) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.fromSocial = fromSocial;
    }

    public void addMemberRole(MemberRole memberRole) {
        roleSet.add(memberRole);
    }
}
