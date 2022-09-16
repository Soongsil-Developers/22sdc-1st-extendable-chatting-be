package com.extendablechattingbe.extendablechattingbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "멤버 반환시 사용되는 DTO")
public class MemberResponse {

    @Schema(description = "멤버 아이디", example = "1")
    private Long id;
    @Schema(description = "멤버 닉네임", example = "멤버 닉네임")
    private String nickname;

    public MemberResponse(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}
