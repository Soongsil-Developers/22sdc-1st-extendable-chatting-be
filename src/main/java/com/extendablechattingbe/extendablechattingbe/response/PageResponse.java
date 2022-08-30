package com.extendablechattingbe.extendablechattingbe.response;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.function.Function;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
@Schema(description="페이지 응답DTO")
@Data
public class PageResponse<DTO,EN> {

    private List<DTO> dtoList;

    private int page;

    private int size;

    private int totalPage;

    public PageResponse(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        size = result.getPageable().getPageSize();
        page = result.getPageable().getPageNumber() + 1;
    }
}
