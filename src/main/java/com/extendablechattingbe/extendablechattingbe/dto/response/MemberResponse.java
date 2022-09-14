package com.extendablechattingbe.extendablechattingbe.dto.response;

import lombok.Getter;

@Getter
public class MemberResponse {

    private Long id;
    private String nickname;

    public MemberResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
