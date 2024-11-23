package com.canvas.application.diary.service;

import com.canvas.application.diary.enums.ReminiscenceType;
import com.canvas.application.diary.exception.DiaryException;
import com.canvas.application.diary.port.in.*;
import com.canvas.application.diary.port.out.DiaryKeywordExtractPort;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.common.page.PageRequest;
import com.canvas.common.page.Slice;
import com.canvas.common.page.Sort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryBasic;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.DiaryOverview;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.domain.diary.enums.Sentiment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryQueryService
        implements GetDiaryUseCase, GetAlbumDiaryUseCase, GetExploreDiaryUseCase, GetReminiscenceDiaryUseCase, GetEmotionStatsUseCase {

    private final DiaryManagementPort diaryManagementPort;
    private final DiaryKeywordExtractPort diaryKeywordExtractPort;

    @Override
    public GetDiaryUseCase.Response.DiaryInfo getDiary(GetDiaryUseCase.Query.Diary query) {
        DomainId diaryId = DomainId.from(query.diaryId());
        DomainId userId = DomainId.from(query.userId());

        DiaryComplete diary = diaryManagementPort.getById(diaryId);

        if (!diary.getIsPublic() && !diary.isWriter(userId)) {
            throw new DiaryException.DiaryForbiddenException();
        }

        return new GetDiaryUseCase.Response.DiaryInfo(
                diary.getId().toString(),
                diary.getContent(),
                diary.getWeightedContents(),
                diary.getEmotion(),
                diary.getLikeCount(),
                diary.isLiked(userId),
                diary.isWriter(userId),
                diary.getDate(),
                diary.getIsPublic(),
                diary.getImages().stream()
                     .map(image -> new GetDiaryUseCase.Response.DiaryInfo.ImageInfo(
                             image.getId().toString(),
                             image.getIsMain(),
                             image.getImageUrl()
                     )).toList()
        );
    }

    @Override
    public GetDiaryUseCase.Response.HomeCalendar getHomeCalendar(GetDiaryUseCase.Query.HomeCalendar query) {
        LocalDate startDate = query.date().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = query.date().with(TemporalAdjusters.lastDayOfMonth());

        List<DiaryBasic> diaries = diaryManagementPort.getByWriterIdAndDateBetween(
                DomainId.from(query.userId()),
                startDate,
                endDate
        );

        return new GetDiaryUseCase.Response.HomeCalendar(
                diaries.stream()
                        .map(diary -> new GetDiaryUseCase.Response.HomeCalendar.DiaryInfo(
                                diary.getId().toString(),
                                diary.getDate(),
                                diary.getEmotion()
                        )).toList()
        );
    }

    @Override
    public GetDiaryUseCase.Response.LikedDiaries getLikedDiaries(GetDiaryUseCase.Query.LikedDiaries query) {
        Slice<DiaryOverview> slice = diaryManagementPort.getLikedDiaries(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")),
                DomainId.from(query.userId())
        );

        return new GetDiaryUseCase.Response.LikedDiaries(
                slice.content().stream()
                     .map(diary -> new GetDiaryUseCase.Response.LikedDiaries.DiaryInfo(
                             diary.getId().toString(),
                             diary.getMainImageOrDefault()))
                     .toList(),
                slice.size(),
                slice.number(),
                slice.hasNext()
        );
    }

    @Override
    public GetAlbumDiaryUseCase.Response getAlbum(GetAlbumDiaryUseCase.Query.Recent query) {
        Slice<DiaryOverview> slice = diaryManagementPort.getAlbum(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")),
                DomainId.from(query.userId())
        );

        return toAlbumResponse(slice);
    }

    @Override
    public GetAlbumDiaryUseCase.Response getAlbumByContent(GetAlbumDiaryUseCase.Query.Content query) {
        Slice<DiaryOverview> slice = diaryManagementPort.getAlbumByContent(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")),
                DomainId.from(query.userId()),
                query.content()
        );

        return toAlbumResponse(slice);
    }

    @Override
    public GetAlbumDiaryUseCase.Response getAlbumByEmotion(GetAlbumDiaryUseCase.Query.Emotion query) {
        Slice<DiaryOverview> slice = diaryManagementPort.getAlbumByEmotion(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")),
                DomainId.from(query.userId()),
                Emotion.parse(query.emotion())
        );

        return toAlbumResponse(slice);
    }

    @Override
    public GetAlbumDiaryUseCase.Response getAlbumByContentAndEmotion(GetAlbumDiaryUseCase.Query.All query) {
        Slice<DiaryOverview> slice = diaryManagementPort.getAlbumByContentAndEmotion(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")),
                DomainId.from(query.userId()),
                query.content(),
                Emotion.parse(query.emotion())
        );

        return toAlbumResponse(slice);
    }

    private static GetAlbumDiaryUseCase.Response toAlbumResponse(Slice<DiaryOverview> slice) {
        return new GetAlbumDiaryUseCase.Response(
                slice.content().stream()
                     .map(diary -> new GetAlbumDiaryUseCase.Response.DiaryInfo(
                             diary.getId().toString(),
                             diary.getMainImageOrDefault()))
                     .toList(),
                slice.size(),
                slice.number(),
                slice.hasNext()
        );
    }

    @Override
    public GetExploreDiaryUseCase.Response getExploreByLatest(GetExploreDiaryUseCase.Query query) {
        Slice<DiaryComplete> slice = diaryManagementPort.getExploreByLatest(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")));

        return toExploreResponse(DomainId.from(query.userId()), slice);
    }

    @Override
    public GetExploreDiaryUseCase.Response getExploreByLike(GetExploreDiaryUseCase.Query query) {
        Slice<DiaryComplete> slice = diaryManagementPort.getExploreByLike(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")));

        return toExploreResponse(DomainId.from(query.userId()), slice);
    }

    private static GetExploreDiaryUseCase.Response toExploreResponse(DomainId userId, Slice<DiaryComplete> slice) {
        return new GetExploreDiaryUseCase.Response(
                slice.content().stream()
                        .map(diary -> new GetExploreDiaryUseCase.Response.DiaryInfo(
                                diary.getId().toString(),
                                diary.getMainImageOrDefault(),
                                diary.isLiked(userId)))
                        .toList(),
                slice.size(),
                slice.number(),
                slice.hasNext()
        );
    }

    // 회고 조회 기준
    // 1년 전 확인
    // 1달 전 확인
    // 1주일 이상의 일기에서 키워드 확인
    @Override
    public GetReminiscenceDiaryUseCase.Response getReminiscenceDiary(GetReminiscenceDiaryUseCase.Query query) {
        DomainId userId = DomainId.from(query.userId());
        LocalDate date = query.date();
        List<String> keywords = diaryKeywordExtractPort.keywordExtract(query.content());

        DiaryComplete reminiscenceDiary = diaryManagementPort.getByWriterIdAndDate(
                userId,
                date.minusYears(1),
                date.minusMonths(1)
        ).orElseGet(() -> findDiaryByKeywords(userId, keywords, date));

        ReminiscenceType type = getReminiscenceType(reminiscenceDiary, query.date());


        return ToReminiscenceDiaryResponse(reminiscenceDiary, userId, type);
    }

    private DiaryComplete findDiaryByKeywords(DomainId userId, List<String> keywords, LocalDate date) {
        return diaryManagementPort.getByWriterIdAndKeywords(
                        userId,
                        keywords,
                        date.minusWeeks(1)
                ).stream()
                .findAny()
                .orElseThrow(DiaryException.DiaryNotFoundException::new);
    }

    private ReminiscenceType getReminiscenceType(DiaryComplete reminiscenceDiary, LocalDate date) {
        LocalDate diaryDate = reminiscenceDiary.getDate();

        if(date.minusMonths(1).equals(diaryDate)) {
            return ReminiscenceType.MONTH;
        }
        if(date.minusYears(1).equals(diaryDate)) {
            return ReminiscenceType.YEAR;
        }

        return ReminiscenceType.KEYWORD;
    }

    private static GetReminiscenceDiaryUseCase.Response ToReminiscenceDiaryResponse(DiaryComplete diary, DomainId userId, ReminiscenceType type) {

        return new GetReminiscenceDiaryUseCase.Response(
                diary.getId().toString(),
                diary.getContent(),
                diary.getEmotion(),
                diary.getLikeCount(),
                diary.isLiked(userId),
                diary.getDate(),
                diary.getImages().stream()
                        .map(
                            image -> new GetReminiscenceDiaryUseCase.Response.ImageInfo(
                                image.getId().toString(),
                                image.getIsMain(),
                                image.getImageUrl()))
                        .toList(),
                type
        );
    }


    @Override
    public GetEmotionStatsUseCase.Response getWeekEmotionStats(GetEmotionStatsUseCase.Query.Week query) {
        LocalDate startDate = query.date().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).minusWeeks(5);
        LocalDate endDate = query.date().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));

        List<DiaryBasic> diaries = diaryManagementPort.getByWriterIdAndDateBetween(
                DomainId.from(query.userId()),
                startDate,
                endDate
        );

        Map<Long, Map<Sentiment, Long>> barData = diaries.stream()
                .collect(Collectors.groupingBy(
                        diary -> ChronoUnit.DAYS.between(diary.getDate(), endDate) / 7,
                        Collectors.groupingBy(
                                diary -> diary.getEmotion().getSentiment(),
                                Collectors.counting())
                ));

        Map<Emotion, Long> pieData = diaries.stream()
                .filter(diary -> diary.getDate().isAfter(endDate.minusWeeks(1)))
                .collect(Collectors.groupingBy(DiaryBasic::getEmotion, Collectors.counting()));

        return toEmotionStatsResponse(barData, pieData);
    }

    @Override
    public GetEmotionStatsUseCase.Response getMonthEmotionStats(GetEmotionStatsUseCase.Query.Month query) {
        LocalDate startDate = query.date().with(TemporalAdjusters.firstDayOfMonth()).minusMonths(5);
        LocalDate endDate = query.date().with(TemporalAdjusters.lastDayOfMonth());

        List<DiaryBasic> diaries = diaryManagementPort.getByWriterIdAndDateBetween(
                DomainId.from(query.userId()),
                startDate,
                endDate
        );

        Map<Long, Map<Sentiment, Long>> barData = diaries.stream()
                .collect(Collectors.groupingBy(
                        diary -> ChronoUnit.MONTHS.between(
                                YearMonth.from(diary.getDate()),
                                YearMonth.from(endDate)),
                        Collectors.groupingBy(
                                diary -> diary.getEmotion().getSentiment(),
                                Collectors.counting())
                ));

        LocalDate previousMonthLastDate = endDate.minusMonths(1)
                                                 .withDayOfMonth(endDate.minusMonths(1).lengthOfMonth());


        Map<Emotion, Long> pieData = diaries.stream()
                .filter(diary -> diary.getDate().isAfter(previousMonthLastDate))
                .collect(Collectors.groupingBy(DiaryBasic::getEmotion, Collectors.counting()));

        return toEmotionStatsResponse(barData, pieData);
    }

    private static GetEmotionStatsUseCase.Response toEmotionStatsResponse(
            Map<Long, Map<Sentiment, Long>> barData,
            Map<Emotion, Long> pieData
    ) {
        return new GetEmotionStatsUseCase.Response(
                barData.entrySet()
                       .stream()
                       .map(entry -> new GetEmotionStatsUseCase.Response.BarData(
                               entry.getKey(),
                               entry.getValue().getOrDefault(Sentiment.POSITIVE, 0L),
                               entry.getValue().getOrDefault(Sentiment.NEUTRAL, 0L),
                               entry.getValue().getOrDefault(Sentiment.NEGATIVE, 0L)))
                       .sorted(Comparator.comparing(GetEmotionStatsUseCase.Response.BarData::dataKey, Comparator.reverseOrder()))
                       .toList(),
                pieData.entrySet()
                       .stream()
                       .map(entry -> new GetEmotionStatsUseCase.Response.PieData(
                               entry.getKey(),
                               entry.getValue()))
                       .toList()
        );
    }

}
