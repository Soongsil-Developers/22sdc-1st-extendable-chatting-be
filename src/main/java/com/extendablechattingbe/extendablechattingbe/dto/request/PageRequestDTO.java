package com.extendablechattingbe.extendablechattingbe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Schema(description="페이지 요청DTO")
@Getter
@AllArgsConstructor
public class PageRequestDTO {

    @Schema(description="페이지 번호",defaultValue = "1")
    private int page;

    @Schema(description="페이지 사이즈",defaultValue = "10")
    private int size;


}
