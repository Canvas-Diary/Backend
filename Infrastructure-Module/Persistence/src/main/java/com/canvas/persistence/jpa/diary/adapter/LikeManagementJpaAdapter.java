package com.canvas.persistence.jpa.diary.adapter;

import com.canvas.application.like.exception.LikeException;
import com.canvas.application.like.port.out.LikeManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Like;
import com.canvas.persistence.jpa.diary.LikeMapper;
import com.canvas.persistence.jpa.diary.repository.LikeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeManagementJpaAdapter implements LikeManagementPort {

    private final LikeJpaRepository likeJpaRepository;

    @Override
    public void add(Like like) {
        if (likeJpaRepository.existsByUserIdAndDiaryId(like.getUserId().value(), like.getDiaryId().value())) {
            throw new LikeException.LikeAlreadyExistsException();
        }

        likeJpaRepository.save(LikeMapper.toEntity(like));
    }

    @Override
    public void remove(DomainId userId, DomainId diaryId) {
        if (!likeJpaRepository.existsByUserIdAndDiaryId(userId.value(), diaryId.value())) {
            throw new LikeException.LikeNotFoundException();
        }

        likeJpaRepository.deleteByUserIdAndDiaryId(userId.value(), diaryId.value());
    }

}
