package com.extendablechattingbe.extendablechattingbe.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;




}
