package com.canvas.application.diary.port.in;

public interface ModifyDiaryUseCase {
    void modify(Command command);

    record Command(
            String diaryId,
            String content,
            Boolean isPublic
    ) {
    }
}
