package com.extendablechattingbe.extendablechattingbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 요청시 사용되는 DTO")
public class MemberRequest {

    @Schema(description = "멤버의 닉네임")
    @NotEmpty
    private String nickname;

}
