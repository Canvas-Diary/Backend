package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "일기 생성 응답")
public record CreateDiaryResponse(
        @Schema(description = "생성된 일기 ID")
        String DiaryId
) {
}
