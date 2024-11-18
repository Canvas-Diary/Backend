package com.canvas.application.like.port.in;

public interface CancelLikeUseCase {
    void cancel(Command command);

    record Command(
            String userId,
            String diaryId
    ) {}
}
