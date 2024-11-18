package com.canvas.application.keyword.port.in;

import java.time.LocalDate;
import java.util.List;

public interface GetKeywordStatsUseCase {

    Response getWeekKeywordStats(Query.Week query);
    Response getMonthKeywordStats(Query.Month query);

    record Query(
    ) {
        public record Week(
            String userId,
            LocalDate date
        ) {

        }

        public record Month(
            String userId,
            LocalDate date
        ) {

        }
    }

    record Response(
        List<StaticInfo> keywordData
    ) {
        public record StaticInfo (
                String name,
                Integer value
        ) {}
    }
}
