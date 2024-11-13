package com.canvas.application.diary.port.in;

import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface GetReminiscenceDiaryUseCase {
    Response getReminiscenceDiary(Query query);

    record Query (
            String userId,
            String content,
            LocalDate date
    ){

    }

    record Response (
        String diaryId,
        String content,
        Emotion emotion,
        Integer likedCount,
        Boolean isLiked,
        LocalDate date,
        List<ImageInfo> images,
        List<String> keywords
        ) {

        public record ImageInfo(
                String imageId,
                Boolean isMain,
                String imageUrl
        ) {}
    }



}
