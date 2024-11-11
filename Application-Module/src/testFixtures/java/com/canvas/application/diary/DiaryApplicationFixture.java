package com.canvas.application.diary;

import com.canvas.application.common.enums.Style;
import com.canvas.application.diary.port.in.*;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.domain.user.entity.User;

import java.time.LocalDate;

public class DiaryApplicationFixture {
    public static AddDiaryUseCase.Command getAddDiaryCommand(User user, DiaryComplete diary) {
        return new AddDiaryUseCase.Command(
                user.getId().toString(),
                diary.getDate(),
                diary.getContent(),
                Style.PHOTOREALISTIC,
                diary.getIsPublic()
        );
    }

    public static ModifyDiaryUseCase.Command getModifyDiaryCommand(User user, DiaryComplete diary, String content, Boolean isPublic) {
        return new ModifyDiaryUseCase.Command(
                user.getId().toString(),
                diary.getId().toString(),
                content,
                isPublic
        );
    }

    public static RemoveDiaryUseCase.Command getRemoveDiaryCommand(User user, DiaryComplete diary) {
        return new RemoveDiaryUseCase.Command(
                diary.getId().toString(),
                user.getId().toString()
        );
    }

    public static GetDiaryUseCase.Query.Diary getGetDiaryQuery(User user, DiaryComplete diary) {
        return new GetDiaryUseCase.Query.Diary(
                user.getId().toString(),
                diary.getId().toString()
        );
    }

    public static GetDiaryUseCase.Query.HomeCalendar getHomeCalendarQuery(User user, LocalDate date) {
        return new GetDiaryUseCase.Query.HomeCalendar(
                user.getId().toString(),
                date
        );
    }

    public static GetDiaryUseCase.Query.LikedDiaries getLikedDiariesQuery(User user) {
        return new GetDiaryUseCase.Query.LikedDiaries(
                user.getId().toString(),
                0,
                9
        );
    }

    public static GetAlbumDiaryUseCase.Query.Recent getAlbumDiariesQuery(User user) {
        return new GetAlbumDiaryUseCase.Query.Recent(
                user.getId().toString(),
                0,
                9
        );
    }

    public static GetAlbumDiaryUseCase.Query.Content getAlbumDiariesByContentQuery(User user, String content) {
        return new GetAlbumDiaryUseCase.Query.Content(
                user.getId().toString(),
                content,
                0,
                9
        );
    }

    public static GetAlbumDiaryUseCase.Query.Emotion getAlbumDiariesByEmotionQuery(User user, Emotion emotion) {
        return new GetAlbumDiaryUseCase.Query.Emotion(
                user.getId().toString(),
                emotion.name(),
                0,
                9
        );
    }

    public static GetAlbumDiaryUseCase.Query.All getAlbumDiariesByContentAndEmotionQuery(User user, String content, Emotion emotion) {
        return new GetAlbumDiaryUseCase.Query.All(
                user.getId().toString(),
                content,
                emotion.name(),
                0,
                9
        );
    }

    public static GetExploreDiaryUseCase.Query getExploreDiaryQuery(User user) {
        return new GetExploreDiaryUseCase.Query(
                user.getId().toString(),
                0,
                9
        );
    }
}
