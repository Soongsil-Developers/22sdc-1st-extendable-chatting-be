package com.extendablechattingbe.extendablechattingbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResponse<DTO, EN> {

    @Schema(description = "페이지 내용")
    private List<DTO> contents;

    @Schema(description = "페이지 번호")
    private int page;

    @Schema(description = "페이지 사이즈")
    private int size;

    @Schema(description = "페이지 전체 사이즈")
    private int totalPage;

    public PageResponse(Page<EN> result, Function<EN, DTO> fn) {
        contents = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        size = result.getPageable().getPageSize();
        page = result.getPageable().getPageNumber() + 1;
    }
}
