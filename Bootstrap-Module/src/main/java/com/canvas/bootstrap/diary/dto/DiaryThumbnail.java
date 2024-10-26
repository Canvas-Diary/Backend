package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "일기 썸네일 정보")
public record DiaryThumbnail(
        @Schema(description = "일기 ID")
        String diaryId,
        @Schema(description = "일기 메인 이미지 url")
        String mainImageUrl
) {}

