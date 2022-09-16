package com.extendablechattingbe.extendablechattingbe.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Schema(description = "페이지 반환시 사용되는 DTO")
public class PageResponse<DTO, EN> {

    @Schema(description = "페이지 내용", example = "Entity List")
    private List<DTO> contents;

    @Schema(description = "페이지 번호", example = "1")
    private int page;

    @Schema(description = "페이지 사이즈", example = "10")
    private int size;

    @Schema(description = "페이지 전체 사이즈", example = "5")
    private int totalPage;

    public PageResponse(Page<EN> result, Function<EN, DTO> fn) {
        contents = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        size = result.getPageable().getPageSize();
        page = result.getPageable().getPageNumber() + 1;
    }
}
