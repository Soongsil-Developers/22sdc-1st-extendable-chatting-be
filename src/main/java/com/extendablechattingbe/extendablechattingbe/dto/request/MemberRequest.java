package com.extendablechattingbe.extendablechattingbe.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MemberRequest {

    @NotEmpty
    private String nickname;

}
