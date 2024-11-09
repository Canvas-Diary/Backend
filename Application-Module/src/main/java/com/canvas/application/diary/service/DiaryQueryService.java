package com.canvas.application.diary.service;

import com.canvas.application.diary.exception.DiaryException;
import com.canvas.application.diary.port.in.GetAlbumDiaryUseCase;
import com.canvas.application.diary.port.in.GetDiaryUseCase;
import com.canvas.application.diary.port.in.GetExploreDiaryUseCase;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.common.page.PageRequest;
import com.canvas.common.page.Slice;
import com.canvas.common.page.Sort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryBasic;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.DiaryOverview;
import com.canvas.domain.diary.enums.Emotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryQueryService
        implements GetDiaryUseCase, GetAlbumDiaryUseCase, GetExploreDiaryUseCase {

    private final DiaryManagementPort diaryManagementPort;

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
        List<DiaryBasic> diaries = diaryManagementPort.getByUserIdAndMonth(
                DomainId.from(query.userId()),
                query.date()
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
}
