package com.canvas.application.image.port.in;

public interface RemoveImageUseCase {
    void remove(Command command);

    record Command(
            Long userId,
            Long diaryId,
            Long imageId
    ) {
    }
}
