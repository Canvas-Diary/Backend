package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, UUID> {
    @Query("""
        select i
        from ImageEntity i
        join i.diaryEntity d
        where i.id = :imageId and d.writerId = :userId
    """)
    Optional<ImageEntity> findByIdAndWriterId(UUID imageId, UUID userId);

    @Query("""
        select i
        from ImageEntity i
        join i.diaryEntity d
        where d.id = (
            select i_sub.diaryId
            from ImageEntity i_sub
            where i_sub.id = :imageId
        ) and d.writerId = :userId
    """)
    List<ImageEntity> findAllInDiaryByIdAndWriterId(UUID imageId, UUID userId);
}
