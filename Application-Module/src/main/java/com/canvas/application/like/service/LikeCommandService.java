package com.canvas.application.like.service;

import com.canvas.application.like.in.AddLikeUseCase;
import com.canvas.application.like.in.CancelLikeUseCase;
import com.canvas.application.like.out.LikeManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeCommandService implements AddLikeUseCase, CancelLikeUseCase {

    private final LikeManagementPort likeManagementPort;

    @Override
    public void add(AddLikeUseCase.Command command) {
        likeManagementPort.add(
                new Like(
                        DomainId.generate(),
                        DomainId.from(command.userId()),
                        DomainId.from(command.diaryId())
                )
        );
    }

    @Override
    public void cancel(CancelLikeUseCase.Command command) {
        likeManagementPort.remove(
                DomainId.from(command.userId()),
                DomainId.from(command.diaryId())
        );
    }
}
