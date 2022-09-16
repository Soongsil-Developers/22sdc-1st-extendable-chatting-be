package com.extendablechattingbe.extendablechattingbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class MemberResponse {

    @Schema(description = "멤버 아이디")
    private Long id;
    @Schema(description = "멤버 닉네임")
    private String nickname;

    public MemberResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
