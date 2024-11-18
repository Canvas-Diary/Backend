package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.DiaryKeywordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DiaryKeywordJpaRepository extends JpaRepository<DiaryKeywordEntity, UUID> {

    @Query("""
        select dk
        from DiaryKeywordEntity dk
        where dk.diaryId in (
                        select d.id
                        from DiaryEntity d
                        where d.writerId = :writerId and d.date between :startDate and :endDate
                        )
    """)
    List<DiaryKeywordEntity> getByWriteIdAndBetween(UUID writerId, LocalDate startDate, LocalDate endDate);

}
