package com.canvas.application.diary.port.in;

import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface GetDiaryUseCase {
    Response.DiaryInfo getMyDiary(Query.Diary query);
    Response.DiaryInfo getOtherDiary(Query.Diary query);
    Response.HomeCalendar getHomeCalendar(Query.HomeCalendar query);

    class Query {
        public record Diary(
                String userId,
                String diaryId
        ) {}

        public record HomeCalendar(
                String userId,
                LocalDate date
        ) {}
    }

    class Response {
        public record DiaryInfo(
                String diaryId,
                String content,
                Emotion emotion,
                Integer likeCount,
                Boolean isLiked,
                LocalDate date,
                Boolean isPublic,
                List<ImageInfo> images
        ) {
            public record ImageInfo(
                    String imageId,
                    Boolean isMain,
                    String imageUrl
            ) {}
        }

        public record HomeCalendar(
                List<DiaryInfo> diaries
        ) {
            public record DiaryInfo(
                    String diaryId,
                    LocalDate date,
                    Emotion emotion
            ) {}
        }
    }
}
