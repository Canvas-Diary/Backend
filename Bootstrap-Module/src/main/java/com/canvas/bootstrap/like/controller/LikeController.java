package com.canvas.bootstrap.like.controller;

import com.canvas.application.like.in.AddLikeUseCase;
import com.canvas.application.like.in.CancelLikeUseCase;
import com.canvas.application.like.service.LikeCommandService;
import com.canvas.bootstrap.like.api.LikeApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController implements LikeApi {
    private final LikeCommandService likeCommandService;

    @Override
    public void addLike(String userId, String diaryId) {
        likeCommandService.add(new AddLikeUseCase.Command(userId, diaryId));
    }

    @Override
    public void deleteLike(String userId, String diaryId) {
        likeCommandService.cancel(new CancelLikeUseCase.Command(userId, diaryId));
    }
}
