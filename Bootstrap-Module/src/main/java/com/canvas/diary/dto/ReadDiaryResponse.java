package com.canvas.diary.dto;

import com.canvas.domain.diary.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReadDiaryResponse {

    private final String diaryId;
    private final String content;
    private final Emotion emotion;
    private final Long likedCount;
    private final boolean isLiked;
    private final boolean isPublic;
    private final LocalDateTime date;
    private final List<ImageInfo> images;

    @Getter
    @AllArgsConstructor
    public static class ImageInfo {
        private final String imageId;
        private final String imageUrl;
    }
}
