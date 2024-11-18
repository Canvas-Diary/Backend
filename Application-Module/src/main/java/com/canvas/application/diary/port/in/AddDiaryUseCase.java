package com.canvas.application.diary.port.in;

import com.canvas.application.common.enums.Style;

import java.time.LocalDate;
import java.util.List;

public interface AddDiaryUseCase {
    Response add(Command command);

    record Command(
            String userId,
            LocalDate date,
            String content,
            List<String> weightedContent,
            Style style,
            Boolean isPublic
    ) {
    }

    record Response(
            String diaryId
    ) {
    }
}
