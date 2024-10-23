package com.canvas.application.like.in;

public interface CancelLikeUseCase {
    void cancel(Command command);

    record Command(
            String userId,
            String diaryId
    ) {}
}
