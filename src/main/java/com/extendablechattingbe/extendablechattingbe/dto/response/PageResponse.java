package com.extendablechattingbe.extendablechattingbe.dto.response;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResponse<DTO, EN> {

    private List<DTO> contents;

    private int page;

    private int size;

    private int totalPage;

    public PageResponse(Page<EN> result, Function<EN, DTO> fn) {
        contents = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        size = result.getPageable().getPageSize();
        page = result.getPageable().getPageNumber() + 1;
    }
}
