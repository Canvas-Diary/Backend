package com.canvas.domain.diary.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Image {
    private final Long id;
    private final Long diaryId;
    private final Boolean isMain;
    private final String s3Uri;

    public static Image withoutId(Long diaryId, Boolean isMain, String s3Uri) {
        return new Image(null, diaryId, isMain, s3Uri);
    }

    public static Image withId(Long id, Long diaryId, Boolean isMain, String s3Uri) {
        return new Image(id, diaryId, isMain, s3Uri);
    }
}
