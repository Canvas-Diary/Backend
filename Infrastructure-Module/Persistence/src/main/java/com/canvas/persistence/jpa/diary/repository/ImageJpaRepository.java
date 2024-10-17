package com.canvas.persistence.jpa.diary.repository;

import com.canvas.persistence.jpa.diary.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<ImageEntity, Long> {
}
