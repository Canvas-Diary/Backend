package com.canvas.bootstrap.diary.controller;

import com.canvas.application.diary.port.in.*;
import com.canvas.bootstrap.diary.api.DiaryApi;
import com.canvas.bootstrap.diary.dto.*;
import com.canvas.bootstrap.diary.enums.ExploreOrder;
import com.canvas.bootstrap.diary.enums.SearchType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DiaryController implements DiaryApi {

    private final AddDiaryUseCase addDiaryUseCase;
    private final GetDiaryUseCase getDiaryUseCase;
    private final GetAlbumDiaryUseCase getAlbumDiaryUseCase;
    private final ModifyDiaryUseCase modifyDiaryUseCase;
    private final RemoveDiaryUseCase removeDiaryUseCase;

    @Override
    public CreateDiaryResponse createDiary(String userId, CreateDiaryRequest request) {
        AddDiaryUseCase.Response response = addDiaryUseCase.add(
                new AddDiaryUseCase.Command(
                        userId,
                        request.date(),
                        request.content(),
                        request.style(),
                        request.isPublic()
                )
        );

        return new CreateDiaryResponse(response.diaryId());
    }

    @Override
    public ReadMyDiaryResponse readMyDiary(String userId, String diaryId) {
        GetDiaryUseCase.Response.DiaryInfo response = getDiaryUseCase.getMyDiary(new GetDiaryUseCase.Query.Diary(userId, diaryId));

        return new ReadMyDiaryResponse(
                response.diaryId(),
                response.content(),
                response.emotion(),
                response.likeCount(),
                response.isLiked(),
                response.date(),
                response.isPublic(),
                response.images().stream()
                        .map(image -> new ReadMyDiaryResponse.ImageInfo(image.imageId(), image.isMain(), image.imageUrl()))
                        .toList()
        );
    }

    @Override
    public ReadOtherDiaryResponse readOtherDiary(String userId, String diaryId) {
        GetDiaryUseCase.Response.DiaryInfo response = getDiaryUseCase.getOtherDiary(new GetDiaryUseCase.Query.Diary(userId, diaryId));

        return new ReadOtherDiaryResponse(
                response.diaryId(),
                response.content(),
                response.emotion(),
                response.likeCount(),
                response.isLiked(),
                response.date(),
                response.images().stream()
                        .map(image -> new ReadOtherDiaryResponse.ImageInfo(image.imageId(), image.isMain(), image.imageUrl()))
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
    public void updateDiary(String diaryId, UpdateDiaryRequest request) {

    }

    @Override
    public void deleteDiary(String diaryId) {

    }

    @Override
    public SliceResponse<DiaryThumbnail> searchDiary(
            String userId,
            Integer page,
            Integer size,
            SearchType type,
            String value
    ) {
        GetAlbumDiaryUseCase.Response response = switch (type) {
            case CONTENT ->
                    getAlbumDiaryUseCase.getAlbumByContent(new GetAlbumDiaryUseCase.Query.Content(userId, value, page, size));
            case TAG ->
                    getAlbumDiaryUseCase.getAlbumByEmotion(new GetAlbumDiaryUseCase.Query.Emotion(userId, value, page, size));
            case NONE -> getAlbumDiaryUseCase.getAlbum(new GetAlbumDiaryUseCase.Query.Recent(userId, page, size));
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

    @Override
    public DiaryExploreResponse exploreDiary(ExploreOrder order) {
        return null;
    }
}
