package com.canvas.application.image.port.in;

public interface SetMainImageUseCase {
    void setMain(Command command);

    record Command(
            String userId,
            String diaryId,
            String imageId
    ) {
    }
}
