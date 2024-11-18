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

import java.time.LocalDate;
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
        LocalDate startDate = query.date().minusWeeks(1);
        LocalDate endDate = query.date();

        return getKeywordStats(query.userId(), startDate, endDate);
    }

    @Override
    public Response getMonthKeywordStats(Query.Month query) {
        LocalDate startDate = query.date().minusMonths(4);
        LocalDate endDate = query.date();

        return getKeywordStats(query.userId(), startDate, endDate);
    }

    private Response getKeywordStats(String userId, LocalDate startDate, LocalDate endDate) {
        List<DiaryKeyword> diaryKeywords = diaryKeywordManagementPort.findByWriteIdAndBetween(
                DomainId.from(userId),
                startDate,
                endDate
        );

        List<DomainId> keywordIds = diaryKeywords.stream()
                .map(DiaryKeyword::getKeywordId)
                .collect(Collectors.toSet())
                .stream().toList();

        List<Keyword> keywords = keywordManagementPort.findByKeywordsId(keywordIds);

        Map<String, Long> keywordNameCounts = diaryKeywords.stream()
                .flatMap(diaryKeyword -> keywords.stream()
                        .filter(keyword -> keyword.getId().equals(diaryKeyword.getKeywordId()))
                        .map(Keyword::getKeyword)
                )
                .collect(Collectors.groupingBy(
                        keywordName -> keywordName,
                        Collectors.counting()
                ));

        return new Response(
                keywordNameCounts.entrySet().stream()
                .map(entry -> new Response.StaticInfo(entry.getKey(), entry.getValue().intValue()))
                .toList());
    }
}
