package com.canvas.application.diary.port.in;

import java.util.List;

public interface GetAlbumDiaryUseCase {
    Response getAlbum(Query.Recent query);
    Response getAlbumByContent(Query.Content query);
    Response getAlbumByEmotion(Query.Emotion query);

    class Query {
        public record Recent(
            String userId,
            Integer page,
            Integer size
        ) {}

        public record Content(
                String userId,
                String content,
                Integer page,
                Integer size
        ) {}

        public record Emotion(
                String userId,
                String emotion,
                Integer page,
                Integer size
        ) {}
    }

    record Response(
            List<DiaryInfo> diaries,
            Integer size,
            Integer number,
            Boolean hasNext
    ) {
        public record DiaryInfo(
                String diaryId,
                String mainImageUrl
        ) {}
    }
}
