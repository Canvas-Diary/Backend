package com.canvas.domain.diary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Like {
    private final Long id;
    private final Long userId;
    private final Long diaryId;

    public static Like withoutId(Long userId, Long diaryId) {
        return new Like(null, userId, diaryId);
    }

    public static Like withId(Long id, Long userId, Long diaryId) {
        return new Like(id, userId, diaryId);
    }
}
