package com.canvas.application.image.port.in;

import com.canvas.application.common.enums.Style;

public interface AddImageUseCase {
    void add(Command command);

    record Command(
            Long userId,
            Long diaryId,
            String content,
            Style style
    ) {
    }
}
