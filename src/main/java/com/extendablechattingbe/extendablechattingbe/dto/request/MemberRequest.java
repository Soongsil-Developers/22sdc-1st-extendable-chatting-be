package com.extendablechattingbe.extendablechattingbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "회원가입 요청시 사용되는 DTO")
@Getter
public class MemberRequest {

    @Schema(description = "멤버의 아이디")
    @NotEmpty
    private String nickname;

}
