package com.canvas.domain.diary.repository;

import com.canvas.domain.diary.model.Diary;
import com.canvas.domain.diary.model.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("""
                    select d
                    from Diary d
                    where d.date between :from and :to and d.content like %:content% and d.emotion = :emotion
            """)
    List<Diary> findAllByDateRangeAndContentAndEmotion(
            LocalDate from, LocalDate to, String content, Emotion emotion
    );
}
