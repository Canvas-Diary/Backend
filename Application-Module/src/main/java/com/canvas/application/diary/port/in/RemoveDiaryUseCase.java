package com.canvas.application.diary.port.in;

public interface RemoveDiaryUseCase {
    void remove(Command command);

    record Command(
            String diaryId,
            String userId
    ) {
    }
}
