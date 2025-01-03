package com.canvas.bootstrap.diary.controller;

import com.canvas.application.diary.port.in.*;
import com.canvas.bootstrap.diary.api.DiaryApi;
import com.canvas.bootstrap.diary.dto.*;
import com.canvas.bootstrap.diary.enums.ExploreOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController implements DiaryApi {

    private final AddDiaryUseCase addDiaryUseCase;
    private final GetDiaryUseCase getDiaryUseCase;

    private final GetAlbumDiaryUseCase getAlbumDiaryUseCase;
    private final GetExploreDiaryUseCase getExploreDiaryUseCase;
    private final ModifyDiaryUseCase modifyDiaryUseCase;
    private final RemoveDiaryUseCase removeDiaryUseCase;
    private final GetReminiscenceDiaryUseCase getReminiscenceDiaryUseCase;

    @Override
    public CreateDiaryResponse createDiary(String userId, CreateDiaryRequest request) {
        AddDiaryUseCase.Response response = addDiaryUseCase.add(
                new AddDiaryUseCase.Command(
                        userId,
                        request.date(),
                        request.content(),
                        request.weightedContents(),
                        request.style(),
                        request.isPublic()
                )
        );

        return new CreateDiaryResponse(response.diaryId());
    }

    @Override
    public ReadDiaryResponse readDiary(String userId, String diaryId) {
        GetDiaryUseCase.Response.DiaryInfo response = getDiaryUseCase.getDiary(new GetDiaryUseCase.Query.Diary(userId, diaryId));

        return new ReadDiaryResponse(
                response.diaryId(),
                response.content(),
                response.weightedContents(),
                response.emotion(),
                response.likeCount(),
                response.isLiked(),
                response.isMine(),
                response.date(),
                response.isPublic(),
                response.images().stream()
                        .map(image -> new ReadDiaryResponse.ImageInfo(image.imageId(), image.isMain(), image.imageUrl()))
                        .toList()
        );
    }

    @Override
    public ReadDiaryCalenderResponse readDiaryCalender(String userId, LocalDate date) {
        GetDiaryUseCase.Response.HomeCalendar response =
                getDiaryUseCase.getHomeCalendar(new GetDiaryUseCase.Query.HomeCalendar(userId, date));

        return new ReadDiaryCalenderResponse(
                response.diaries().stream()
                        .map(diaryInfo -> new ReadDiaryCalenderResponse.CalenderInfo(
                                diaryInfo.diaryId(),
                                diaryInfo.date(),
                                diaryInfo.emotion()))
                        .toList());
    }

    @Override
    public void updateDiary(String userId, String diaryId, UpdateDiaryRequest request) {
        modifyDiaryUseCase.modify(new ModifyDiaryUseCase.Command(
                userId, diaryId, request.content(), request.weightedContents(), request.isPublic()
        ));
    }

    @Override
    public void deleteDiary(String userId, String diaryId) {
        removeDiaryUseCase.remove(new RemoveDiaryUseCase.Command(
                diaryId, userId
        ));
    }

    @Override
    public SliceResponse<DiaryThumbnail> searchDiary(
            String userId,
            Integer page,
            Integer size,
            String tag,
            String content
    ) {
        GetAlbumDiaryUseCase.Response response = switch (getQueryType(tag, content)) {
            case "ALL" ->
                    getAlbumDiaryUseCase.getAlbumByContentAndEmotion(new GetAlbumDiaryUseCase.Query.All(userId, content, tag, page, size));
            case "CONTENT" ->
                    getAlbumDiaryUseCase.getAlbumByContent(new GetAlbumDiaryUseCase.Query.Content(userId, content, page, size));
            case "EMOTION" ->
                    getAlbumDiaryUseCase.getAlbumByEmotion(new GetAlbumDiaryUseCase.Query.Emotion(userId, tag, page, size));
            default -> getAlbumDiaryUseCase.getAlbum(new GetAlbumDiaryUseCase.Query.Recent(userId, page, size));
        };

        return new SliceResponse<>(
                response.diaries().stream()
                        .map(diaryInfo -> new DiaryThumbnail(diaryInfo.diaryId(), diaryInfo.mainImageUrl()))
                        .toList(),
                response.size(),
                response.number(),
                response.hasNext()
        );
    }

    private String getQueryType(String tag, String content) {
        if (tag == null && content == null) return "RECENT";
        if (tag == null) return "CONTENT";
        if (content == null) return "EMOTION";
        return "ALL";
    }

    @Override
    public SliceResponse<ExploreDiaryResponse> exploreDiary(String userId, int page, int size, ExploreOrder order) {
        GetExploreDiaryUseCase.Response response = switch (order) {
            case LATEST -> getExploreDiaryUseCase.getExploreByLatest(new GetExploreDiaryUseCase.Query(userId, page, size));
            case POPULARITY -> getExploreDiaryUseCase.getExploreByLike(new GetExploreDiaryUseCase.Query(userId, page, size));
        };

        return new SliceResponse<>(
                response.diaries().stream()
                        .map(diaryInfo -> new ExploreDiaryResponse(
                                diaryInfo.diaryId(),
                                diaryInfo.mainImageUrl(),
                                diaryInfo.isLiked()))
                        .toList(),
                response.size(),
                response.number(),
                response.hasNext()
        );
    }

    @Override
    public SliceResponse<DiaryThumbnail> readLikedDiary(String userId, Integer page, Integer size) {
        GetDiaryUseCase.Response.LikedDiaries response = getDiaryUseCase.getLikedDiaries(
                new GetDiaryUseCase.Query.LikedDiaries(
                        userId,
                        page,
                        size
                )
        );

        return new SliceResponse<>(
                response.diaries().stream()
                        .map(diaryInfo -> new DiaryThumbnail(diaryInfo.diaryId(), diaryInfo.mainImageUrl()))
                        .toList(),
                response.size(),
                response.number(),
                response.hasNext()
        );
    }

    @Override
    public ReminiscenceResponse getReminiscenceDiary(String userId, ReminiscenceRequest request) {
        GetReminiscenceDiaryUseCase.Response response = getReminiscenceDiaryUseCase.getReminiscenceDiary(
                new GetReminiscenceDiaryUseCase.Query(
                    userId,
                    request.content(),
                    request.date()
        ));


        return new ReminiscenceResponse(
                response.diaryId(),
                response.content(),
                response.emotion(),
                response.likedCount(),
                response.isLiked(),
                response.date(),
                response.images()
                    .stream().map(
                        imageInfo -> new ReminiscenceResponse.ImageInfo(
                            imageInfo.imageId(),
                            imageInfo.isMain(),
                            imageInfo.imageUrl())
                    ).toList(),
                response.reminiscenceType()
        );
    }

}
