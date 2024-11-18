package com.canvas.bootstrap.diary.dto;

import com.canvas.application.common.enums.Style;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "일기 생성 요청")
public record CreateDiaryRequest(
        @Schema(description = "일기 생성 시간")
        LocalDate date,
        @Schema(description = "일기 내용")
        String content,
        @Schema(description = "강조할 일기 내용")
        List<String> weightedContent,
        @Schema(description = "일기 화풍")
        Style style,
        @Schema(description = "일기 공개 여부")
        boolean isPublic
) {
}
