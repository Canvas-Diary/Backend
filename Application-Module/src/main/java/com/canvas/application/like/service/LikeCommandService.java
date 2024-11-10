package com.canvas.application.like.service;

import com.canvas.application.like.port.in.AddLikeUseCase;
import com.canvas.application.like.port.in.CancelLikeUseCase;
import com.canvas.application.like.port.out.LikeManagementPort;
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
                Like.create(
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
