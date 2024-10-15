package com.canvas.application.image.port.in;

public interface SetMainImageUseCase {
    void setMain(Command command);

    record Command(
            Long userId,
            Long diaryId,
            Long imageId
    ) {
    }
}
