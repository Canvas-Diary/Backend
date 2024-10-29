package com.canvas.application.image.port.in;

import com.canvas.application.common.enums.Style;

public interface AddImageUseCase {
    Response.add add(Command command);
    Response.create create(Command command);

    record Command(
            String diaryId,
            String content,
            Style style
    ) {
    }

    class Response {
        public record add(
                String imageId,
                Boolean isMain,
                String imageUrl
        ) {
        }

        public record create(
                String imageUrl
        ) {}
    }
}
