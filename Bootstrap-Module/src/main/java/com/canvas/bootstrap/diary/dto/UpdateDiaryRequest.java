package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "일기 수정 요청")
public record UpdateDiaryRequest(
        @Schema(description = "일기 내용")
        String content,
        @Schema(description = "일기 공개 여부")
        Boolean isPublic
) {
}
