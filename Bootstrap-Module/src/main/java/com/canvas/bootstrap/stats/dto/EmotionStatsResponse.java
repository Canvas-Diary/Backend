package com.canvas.bootstrap.stats.dto;

import com.canvas.domain.diary.enums.Emotion;

import java.util.List;

public record EmotionStatsResponse(
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
