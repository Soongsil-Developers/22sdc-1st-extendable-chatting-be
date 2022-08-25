package com.extendablechattingbe.extendablechattingbe.response;

import com.extendablechattingbe.extendablechattingbe.domain.Room;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

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
