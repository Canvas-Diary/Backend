package com.canvas.application.diary.port.in;

import com.canvas.application.diary.enums.Style;

import java.time.LocalDateTime;

public interface AddDiaryUseCase {
    Response add(Command command);

    record Command(
            Long userId,
            LocalDateTime dateTime,
            String content,
            Style style,
            Boolean isPublic
    ) {
    }

    record Response(
            Long diaryId
    ) {
    }
}
