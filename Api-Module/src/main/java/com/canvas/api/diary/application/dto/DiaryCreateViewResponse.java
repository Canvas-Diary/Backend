package com.canvas.api.diary.application.dto;

import java.util.List;

public record DiaryCreateViewResponse(
        String description,
        String emotion,
        String extractedEmotion,
        List<String> canvasImageUrl
) {
}
