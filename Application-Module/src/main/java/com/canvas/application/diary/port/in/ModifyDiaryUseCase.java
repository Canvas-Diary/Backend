package com.canvas.application.diary.port.in;

import com.canvas.application.common.enums.Style;

public interface ModifyDiaryUseCase {
    void modify(Command command);

    record Command(
            String userId,
            String diaryId,
            String content,
            Style style,
            Boolean isPublic
    ) {
    }
}
