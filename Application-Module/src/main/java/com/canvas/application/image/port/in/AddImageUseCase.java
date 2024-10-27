package com.canvas.application.image.port.in;

import com.canvas.application.common.enums.Style;

public interface AddImageUseCase {
    Response add(Command command);

    record Command(
            String diaryId,
            String content,
            Style style
    ) {}

    record Response(
            String imageId,
            Boolean isMain,
            String imageUrl
    ) {}
}
