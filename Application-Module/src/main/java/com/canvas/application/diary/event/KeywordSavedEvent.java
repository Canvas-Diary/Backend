package com.canvas.application.diary.event;

public record KeywordSavedEvent(
        String userId,
        String diaryId,
        String content
) {
}
