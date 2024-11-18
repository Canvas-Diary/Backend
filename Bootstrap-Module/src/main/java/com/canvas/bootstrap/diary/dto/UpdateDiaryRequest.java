package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "일기 수정 요청")
public record UpdateDiaryRequest(
        @Schema(description = "일기 내용")
        String content,
        @Schema(description = "강조할 일기 내용")
        List<String> weightedContent,
        @Schema(description = "일기 공개 여부")
        Boolean isPublic
) {
}
