package com.canvas.application.diary.port.in;

import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface GetEmotionStatsUseCase {
    Response getWeekEmotionStats(Query.Week query);
    Response getMonthEmotionStats(Query.Month query);

    class Query {
        public record Week(
                String userId,
                LocalDate date
        ) {}

        public record Month(
                String userId,
                LocalDate date
        ) {}
    }

    record Response(
            List<BarData> barData,
            List<PieData> pieData
    ) {
        public record BarData(
                Long dataKey,
                Long positive,
                Long neutral,
                Long negative
        ) {
        }

        public record PieData(
                Emotion emotion,
                Long diaryCount
        ) {
        }
    }

}
