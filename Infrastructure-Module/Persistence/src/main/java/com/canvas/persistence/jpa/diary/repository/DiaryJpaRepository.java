package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
        where d.id = :diaryId and d.isPublic = true
    """)
    Optional<DiaryEntity> findPublicById(UUID diaryId);

    @Query("""
        select d
        from DiaryEntity d
        left join fetch d.likeEntities
        where d.id = :diaryId and d.writerId = :writerId
    """)
    Optional<DiaryEntity> findByIdAndWriterId(UUID diaryId, UUID writerId);

    @Query("""
        select d
        from DiaryEntity d
        left join fetch d.likeEntities
        where d.writerId = :writerId and d.createdAt between :start and :end
    """)
    List<DiaryEntity> findByWriterIdAndCreatedAtBetween(UUID writerId, LocalDateTime start, LocalDateTime end);

    Slice<DiaryEntity> findByWriterId(Pageable pageable, UUID writerId);
    Slice<DiaryEntity> findByWriterIdAndContentContains(Pageable pageable, UUID writerId, String content);
    Slice<DiaryEntity> findByWriterIdAndEmotion(Pageable pageable, UUID writerId, String emotion);

    boolean existsByIdAndWriterId(UUID diaryId, UUID writerId);
}
