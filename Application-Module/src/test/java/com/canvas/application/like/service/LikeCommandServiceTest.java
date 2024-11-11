package com.canvas.application.like.service;

import com.canvas.application.like.port.in.AddLikeUseCase;
import com.canvas.application.like.port.in.CancelLikeUseCase;
import com.canvas.application.like.port.out.LikeManagementPort;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.Like;
import com.canvas.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.canvas.application.like.LikeApplicationFixture.getAddLikeCommand;
import static com.canvas.application.like.LikeApplicationFixture.getCancelLikeCommand;
import static com.canvas.domain.fixture.DiaryFixture.PUBLIC_OTHER_DIARY;
import static com.canvas.domain.fixture.UserFixture.MYSELF;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LikeCommandServiceTest {

    @Mock
    private LikeManagementPort likeManagementPort;

    @InjectMocks
    private LikeCommandService likeCommandService;

    @Test
    @DisplayName("좋아요 추가")
    void addTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_OTHER_DIARY.getDiaryComplete();
        AddLikeUseCase.Command command = getAddLikeCommand(user, diary);

        // when
        likeCommandService.add(command);

        // then
        verify(likeManagementPort).add(any(Like.class));
    }

    @Test
    @DisplayName("좋아요 취소")
    void cancelTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_OTHER_DIARY.getDiaryComplete();
        CancelLikeUseCase.Command command = getCancelLikeCommand(user, diary);

        // when
        likeCommandService.cancel(command);

        // then
        verify(likeManagementPort).remove(user.getId(), diary.getId());
    }

}