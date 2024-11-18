package com.canvas.bootstrap.stats.controller;

import com.canvas.application.diary.port.in.GetEmotionStatsUseCase;
import com.canvas.application.keyword.port.in.GetKeywordStatsUseCase;
import com.canvas.bootstrap.stats.api.StatsApi;
import com.canvas.bootstrap.stats.dto.EmotionStatsResponse;
import com.canvas.bootstrap.stats.dto.KeywordStatsResponse;
import com.canvas.bootstrap.stats.type.StatsType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class StatsController implements StatsApi {

    private final GetEmotionStatsUseCase getEmotionStatsUseCase;
    private final GetKeywordStatsUseCase getKeywordStatsUseCase;

    @Override
    public EmotionStatsResponse getEmotionStats(String userId, StatsType type, LocalDate date) {
        GetEmotionStatsUseCase.Response response = switch (type) {
            case WEEK -> getEmotionStatsUseCase.getWeekEmotionStats(new GetEmotionStatsUseCase.Query.Week(userId, date));
            case MONTH -> getEmotionStatsUseCase.getMonthEmotionStats(new GetEmotionStatsUseCase.Query.Month(userId, date));
        };

        return new EmotionStatsResponse(
                response.barData().stream()
                        .map(barData -> new EmotionStatsResponse.BarData(
                                barData.dataKey(),
                                barData.positive(),
                                barData.neutral(),
                                barData.negative()))
                        .toList(),
                response.pieData().stream()
                        .map(pieData -> new EmotionStatsResponse.PieData(
                                pieData.emotion(),
                                pieData.diaryCount()))
                        .toList()
        );
    }

    @Override
    public KeywordStatsResponse getKeywordStats(String userId, StatsType type, LocalDate date) {
        GetKeywordStatsUseCase.Response response = switch (type) {
            case WEEK -> getKeywordStatsUseCase.getWeekKeywordStats(
                    new GetKeywordStatsUseCase.Query.Week(userId, date));
            case MONTH -> getKeywordStatsUseCase.getMonthKeywordStats(
                    new GetKeywordStatsUseCase.Query.Month(userId, date));
        };

        return new KeywordStatsResponse(
                response.keywordData().stream()
                        .map(staticInfo -> new KeywordStatsResponse.StaticInfo(
                                staticInfo.name(),
                                staticInfo.value()
                        ))
                        .toList()
        );
    }

}
