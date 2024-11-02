package com.canvas.bootstrap.image.dto;

import com.canvas.application.common.enums.Style;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이미지 추가 요청")
public record CreateImageRequest(
        @Schema(description = "일기 내용")
        String content,
        @Schema(description = "일기 화풍")
        Style style
) {
}
