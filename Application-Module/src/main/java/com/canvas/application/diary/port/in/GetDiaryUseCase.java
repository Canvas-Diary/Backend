package com.canvas.application.diary.port.in;

import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface GetDiaryUseCase {
    Response.Diary getMyDiary(Query.Diary query);
    Response.Diary getOtherDiary(Query.Diary query);
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
        public record Diary(
                String diaryId,
                String content,
                Emotion emotion,
                Integer likeCount,
                Boolean isLiked,
                List<Image> images
        ) {
            public record Image(
                    String imageId,
                    Boolean isMain,
                    String imageUrl
            ) {}
        }

        public record HomeCalendar(
                List<Diary> diaries
        ) {
            public record Diary(
                    String diaryId,
                    LocalDate date,
                    Emotion emotion
            ) {}
        }
    }
}
