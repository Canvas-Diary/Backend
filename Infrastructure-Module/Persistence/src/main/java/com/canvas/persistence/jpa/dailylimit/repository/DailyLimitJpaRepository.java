package com.canvas.persistence.jpa.dailylimit.repository;

import com.canvas.persistence.jpa.dailylimit.entity.DailyLimitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface DailyLimitJpaRepository extends JpaRepository<DailyLimitEntity, UUID> {
    int countByUserIdAndDate(UUID userId, LocalDate date);
    void deleteDistinctByUserId(UUID userId);
    void deleteByUserId(UUID userId);
}
