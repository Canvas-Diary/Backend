package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiaryJpaRepository extends JpaRepository<DiaryEntity, UUID> {
    boolean existsByWriterIdAndDate(UUID writerId, LocalDate date);

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
        where d.id = :diaryId and d.writerId = :writerId
    """)
    Optional<DiaryEntity> findByIdAndWriterId(UUID diaryId, UUID writerId);

    @Query("""
        select d
        from DiaryEntity d
        left join fetch d.likeEntities
        where d.writerId = :writerId and d.date between :start and :end
        order by d.date asc
    """)
    List<DiaryEntity> findByWriterIdAndDateBetween(UUID writerId, LocalDate start, LocalDate end);

    Slice<DiaryEntity> findByWriterId(Pageable pageable, UUID writerId);
    Slice<DiaryEntity> findByWriterIdAndContentContains(Pageable pageable, UUID writerId, String content);
    Slice<DiaryEntity> findByWriterIdAndEmotion(Pageable pageable, UUID writerId, String emotion);
    Slice<DiaryEntity> findByWriterIdAndContentContainsAndEmotion(Pageable pageable, UUID writerId, String content, String emotion);
    Slice<DiaryEntity> findAllByIsPublicTrue(Pageable pageable);

    @Query("""
        select d
        from DiaryEntity d
        left join LikeEntity l on d.id = l.diaryId
        where d.isPublic = true
        group by d.id
        order by count(l) desc
    """)
    Slice<DiaryEntity> findAllByIsPublicTrueOrderByLikeCountDesc(Pageable pageable);

    @Query("""
        select d
        from DiaryEntity d
        where d.id in (select l.diaryId
                       from LikeEntity l
                       where l.userId = :userId)
    """)
    Slice<DiaryEntity> findByUserLiked(Pageable pageable, UUID userId);

    boolean existsByIdAndWriterId(UUID diaryId, UUID writerId);


    @Query("""
        select d
        from DiaryEntity d
        where d.writerId = :writerId and (d.date = :beforeYear or d.date = :beforeMonth)
        order by d.date asc
    """)
    Optional<DiaryEntity> findByWriterIdAndDate(UUID writerId, LocalDate beforeYear, LocalDate beforeMonth);

    @Query("""
        select d
        from DiaryEntity d
        where d.writerId = :writerId and d.date < :baseDate and d.id in (
            select dk.diaryEntity.id
            from DiaryKeywordEntity dk
            join dk.keywordEntity k
            where k.name in :keywords
        )
    """)
    List<DiaryEntity> findDiariesByWriterIdAndKeywordsBeforeBaseDate(UUID writerId, List<String> keywords, LocalDate baseDate);
}
