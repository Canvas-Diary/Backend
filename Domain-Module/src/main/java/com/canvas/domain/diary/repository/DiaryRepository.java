package com.canvas.domain.diary.repository;

import com.canvas.domain.diary.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long>, CustomDiaryRepository {

}
