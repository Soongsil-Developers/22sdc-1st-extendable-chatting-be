package com.extendablechattingbe.extendablechattingbe.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberRequest {

    @NotEmpty
    private String nickname;

}
