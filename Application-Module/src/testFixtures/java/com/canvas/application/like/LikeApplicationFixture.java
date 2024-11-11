package com.canvas.application.like;

import com.canvas.application.like.port.in.AddLikeUseCase;
import com.canvas.application.like.port.in.CancelLikeUseCase;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.user.entity.User;

public class LikeApplicationFixture {
    public static AddLikeUseCase.Command getAddLikeCommand(User user, DiaryComplete diaryComplete) {
        return new AddLikeUseCase.Command(
                user.getId().toString(),
                diaryComplete.getId().toString()
        );
    }

    public static CancelLikeUseCase.Command getCancelLikeCommand(User user, DiaryComplete diaryComplete) {
        return new CancelLikeUseCase.Command(
                user.getId().toString(),
                diaryComplete.getId().toString()
        );
    }
}
