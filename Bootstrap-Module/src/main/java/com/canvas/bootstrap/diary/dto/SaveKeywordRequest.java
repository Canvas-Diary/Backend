package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "키워드 저장 요청")
public record SaveKeywordRequest(
        @Schema(description = "저장할 키워드 리스트")
        List<String> keywords,
        @Schema(description = "일기 날짜")
        LocalDate date
) {
}
