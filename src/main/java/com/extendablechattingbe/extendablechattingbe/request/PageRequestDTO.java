package com.extendablechattingbe.extendablechattingbe.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
@Schema(description="페이지 요청DTO")
@Getter
public class PageRequestDTO {

    @Schema(description="페이지 번호",defaultValue = "1")
    @Builder.Default
    private int page = 1;

    @Schema(description="페이지 사이즈",defaultValue = "10")
    @Builder.Default
    private int size = 10;




}
