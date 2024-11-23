package com.canvas.application.keyword.service;

import com.canvas.application.keyword.port.in.GetKeywordStatsUseCase;
import com.canvas.application.keyword.port.out.DiaryKeywordManagementPort;
import com.canvas.application.keyword.port.out.KeywordManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.keyword.DiaryKeyword;
import com.canvas.domain.keyword.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordQueryService implements GetKeywordStatsUseCase {

    private final KeywordManagementPort keywordManagementPort;
    private final DiaryKeywordManagementPort diaryKeywordManagementPort;

    @Override
    public Response getWeekKeywordStats(Query.Week query) {
        LocalDate startDate = query.date().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate endDate = query.date().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        return getKeywordStats(query.userId(), startDate, endDate);
    }

    @Override
    public Response getMonthKeywordStats(Query.Month query) {
        LocalDate startDate = query.date().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = query.date().with(TemporalAdjusters.lastDayOfMonth());

        return getKeywordStats(query.userId(), startDate, endDate);
    }

    private Response getKeywordStats(String userId, LocalDate startDate, LocalDate endDate) {


        Map<String, Long> keywordNameCounts = diaryKeywordManagementPort.
                findDiaryKeywordByWriterIdAndDateRange(DomainId.from(userId), startDate, endDate);

        return toKeywordStatsResponse(keywordNameCounts);
    }

    private static Response toKeywordStatsResponse(Map<String, Long> keywordNameCounts) {
        return new Response(
                keywordNameCounts.entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .map(entry -> new Response.StaticInfo(entry.getKey(), entry.getValue().intValue()))
                        .toList()
        );
    }

}
