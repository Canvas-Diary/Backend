package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryJpaRepository extends JpaRepository<DiaryEntity, UUID> {
    @Query("""
        select d
        from DiaryEntity d
        left join fetch d.likeEntities
        where d.id = :diaryId
    """)
    Optional<DiaryEntity> findById(UUID diaryId);

    @Query("""
        select d
        from DiaryEntity d
        left join fetch d.likeEntities
        where d.writerId = :writerId and d.createdAt between :start and :end
    """)
    List<DiaryEntity> findByWriterIdAndCreatedAtBetween(UUID writerId, LocalDateTime start, LocalDateTime end);
}
