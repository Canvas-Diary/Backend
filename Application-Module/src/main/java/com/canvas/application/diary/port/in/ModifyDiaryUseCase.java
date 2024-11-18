package com.canvas.application.diary.port.in;

import java.util.List;

public interface ModifyDiaryUseCase {
    void modify(Command command);

    record Command(
            String userId,
            String diaryId,
            String content,
            List<String> weightedContent,
            Boolean isPublic
    ) {
    }
}
