package com.canvas.application.diary.port.in;

import java.util.List;

public interface GetExploreDiaryUseCase {
    Response getExploreByLatest(Query query);
    Response getExploreByLike(Query query);

    record Query(
            Integer page,
            Integer size
    ) {}

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
