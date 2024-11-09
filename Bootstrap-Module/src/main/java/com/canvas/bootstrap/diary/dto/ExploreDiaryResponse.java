package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ExploreDiaryResponse(
        @Schema(description = "일기 ID")
        String diaryId,
        @Schema(description = "일기 메인 이미지 url")
        String mainImageUrl,
        @Schema(description = "좋아요 선택 여부")
        Boolean isLiked
) {}
