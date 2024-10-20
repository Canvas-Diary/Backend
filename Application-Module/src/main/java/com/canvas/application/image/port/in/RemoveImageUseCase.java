package com.canvas.application.image.port.in;

public interface RemoveImageUseCase {
    void remove(Command command);

    record Command(
            String userId,
            String diaryId,
            String imageId
    ) {
    }
}
