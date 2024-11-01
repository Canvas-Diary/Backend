package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "페이징 응답 객체")
public record SliceResponse<T>(
        @Schema(description = "페이징되는 내용")
        List<T> content,
        @Schema(description = "페이지 크기")
        Integer size,
        @Schema(description = "페이지 번호")
        Integer number,
        @Schema(description = "다음 페이지 존재 여부")
        Boolean hasNext
) {
}
