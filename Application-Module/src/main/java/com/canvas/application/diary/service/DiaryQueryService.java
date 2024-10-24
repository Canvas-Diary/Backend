package com.canvas.application.diary.service;

import com.canvas.application.diary.port.in.GetDiaryUseCase;
import com.canvas.application.diary.port.in.GetAlbumDiaryUseCase;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.common.page.PageRequest;
import com.canvas.common.page.Slice;
import com.canvas.common.page.Sort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.enums.Emotion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryQueryService implements GetDiaryUseCase, GetAlbumDiaryUseCase {

    private static final String DEFAULT_IMAGE_URL = "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/sample.jpg";

    private final DiaryManagementPort diaryManagementPort;

    @Override
    public GetDiaryUseCase.Response.Diary getMyDiary(GetDiaryUseCase.Query.Diary query) {
        Diary diary = diaryManagementPort.getByIdAndWriterId(
                DomainId.from(query.diaryId()),
                DomainId.from(query.userId())
        );

        return toResponseDiary(query.userId(), diary);
    }

    @Override
    public GetDiaryUseCase.Response.Diary getOtherDiary(GetDiaryUseCase.Query.Diary query) {
        Diary diary = diaryManagementPort.getPublicById(DomainId.from(query.diaryId()));

        return toResponseDiary(query.userId(), diary);
    }

    @Override
    public GetDiaryUseCase.Response.HomeCalendar getHomeCalendar(GetDiaryUseCase.Query.HomeCalendar query) {
        List<Diary> diaries = diaryManagementPort.getByUserIdAndMonth(
                DomainId.from(query.userId()),
                query.date()
        );

        return new GetDiaryUseCase.Response.HomeCalendar(
                diaries.stream()
                        .map(diary -> new GetDiaryUseCase.Response.HomeCalendar.Diary(
                                diary.getId().toString(),
                                diary.getDateTime().toLocalDate(),
                                diary.getDiaryContent().getEmotion()
                        )).toList()
        );
    }

    private static GetDiaryUseCase.Response.Diary toResponseDiary(String userId, Diary diary) {
        return new GetDiaryUseCase.Response.Diary(
                diary.getId().toString(),
                diary.getDiaryContent().getContent(),
                diary.getDiaryContent().getEmotion(),
                diary.getLikes().size(),
                diary.getLikes().stream()
                        .anyMatch(like -> like.getUserId().equals(DomainId.from(userId))),
                diary.getDiaryContent().getImages().stream()
                        .map(image -> new GetDiaryUseCase.Response.Diary.Image(
                                image.getId().toString(),
                                image.getIsMain(),
                                image.getS3Uri()
                        )).toList()
        );
    }

    @Override
    public GetAlbumDiaryUseCase.Response getAlbum(GetAlbumDiaryUseCase.Query.Recent query) {
        Slice<Diary> slice = diaryManagementPort.getAlbum(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")),
                DomainId.from(query.userId())
        );

        return toResponseAlbum(slice);
    }

    @Override
    public GetAlbumDiaryUseCase.Response getAlbumByContent(GetAlbumDiaryUseCase.Query.Content query) {
        Slice<Diary> slice = diaryManagementPort.getAlbumByContent(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")),
                DomainId.from(query.userId()),
                query.content()
        );

        return toResponseAlbum(slice);
    }

    @Override
    public GetAlbumDiaryUseCase.Response getAlbumByEmotion(GetAlbumDiaryUseCase.Query.Emotion query) {
        Slice<Diary> slice = diaryManagementPort.getAlbumByEmotion(
                new PageRequest(query.page(), query.size(), Sort.by(Sort.Direction.DESC, "createdAt")),
                DomainId.from(query.userId()),
                Emotion.parse(query.emotion())
        );

        return toResponseAlbum(slice);
    }

    private static GetAlbumDiaryUseCase.Response toResponseAlbum(Slice<Diary> slice) {
        return new GetAlbumDiaryUseCase.Response(
                slice.content().stream()
                        .map(diary -> new GetAlbumDiaryUseCase.Response.DiaryInfo(
                                diary.getId().toString(),
                                diary.getDiaryContent().getImages().stream()
                                        .filter(Image::getIsMain)
                                        .map(Image::getS3Uri)
                                        .findFirst()
                                        .orElse(DEFAULT_IMAGE_URL)))
                        .toList(),
                slice.size(),
                slice.number(),
                slice.hasNext()
        );
    }

}
