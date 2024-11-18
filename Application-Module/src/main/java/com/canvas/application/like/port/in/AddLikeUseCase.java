package com.canvas.application.like.port.in;

public interface AddLikeUseCase {
    void add(Command command);

    record Command(
            String userId,
            String diaryId
    ) {}
}
