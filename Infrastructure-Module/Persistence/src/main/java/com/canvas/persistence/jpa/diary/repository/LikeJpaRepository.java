package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LikeJpaRepository extends JpaRepository<LikeEntity, UUID> {
    boolean existsByUserIdAndDiaryId(UUID userId, UUID diaryId);
    void deleteByUserIdAndDiaryId(UUID userId, UUID diaryId);
}
