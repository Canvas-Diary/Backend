package com.canvas.application.image.port.in;

import com.canvas.application.common.enums.Style;

public interface AddImageUseCase {
    Response.Add add(Command.Add command);
    Response.Create create(Command.Create command);

    class Command {
        public record Add(
                String userId,
                String diaryId,
                Style style
        ) {
        }

        public record Create(
                String userId,
                String content,
                String joinedWeightedContents,
                Style style
        ) {
        }
    }

    class Response {
        public record Add(
                String imageId,
                Boolean isMain,
                String imageUrl
        ) {
        }

        public record Create(
                String imageUrl
        ) {}
    }
}
