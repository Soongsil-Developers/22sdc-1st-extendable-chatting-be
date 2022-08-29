package com.extendablechattingbe.extendablechattingbe.response;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;
@Schema(description="페이지 응답DTO")
public class PageResponse<T> {

    private List<T> dtoList;

    private int page;

    private int size;

    private int totalSize;

    @Builder
    public PageResponse(List<T> dtoList, int page, int size, int totalSize) {
        this.dtoList = dtoList;
        this.page = page;
        this.size = size;
        this.totalSize = totalSize;
    }
}
