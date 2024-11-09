package com.canvas.application.diary.port.in;

public interface ModifyDiaryUseCase {
    void modify(Command command);

    record Command(
            String userId,
            String diaryId,
            String content,
            Boolean isPublic
    ) {
    }
}
