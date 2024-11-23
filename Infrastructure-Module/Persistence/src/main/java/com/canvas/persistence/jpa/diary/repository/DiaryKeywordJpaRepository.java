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
    join fetch dk.keywordEntity k
    where dk.diaryEntity.writerId = :writerId
      and dk.diaryEntity.date between :startDate and :endDate
""")
    List<DiaryKeywordEntity> findByWriterIdAndDateRange(UUID writerId, LocalDate startDate, LocalDate endDate);


}
