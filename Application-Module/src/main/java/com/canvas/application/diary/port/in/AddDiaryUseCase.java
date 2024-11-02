package com.canvas.application.diary.port.in;

import com.canvas.application.common.enums.Style;

import java.time.LocalDate;

public interface AddDiaryUseCase {
    Response add(Command command);

    record Command(
            String userId,
            LocalDate date,
            String content,
            Style style,
            Boolean isPublic
    ) {
    }

    record Response(
            String diaryId
    ) {
    }
}
