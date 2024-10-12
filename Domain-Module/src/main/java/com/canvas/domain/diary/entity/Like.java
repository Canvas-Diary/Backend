package com.canvas.domain.diary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Like {
    private final Long id;
    private final Long userId;

    public static Like withoutId(Long userId) {
        return new Like(null, userId);
    }

    public static Like withId(Long id, Long userId) {
        return new Like(id, userId);
    }
}
