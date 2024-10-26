package com.canvas.bootstrap.image.dto;

import com.canvas.application.common.enums.Style;

public record CreateImageRequest(
        String diaryId,
        String content,
        Style style
) {
}
