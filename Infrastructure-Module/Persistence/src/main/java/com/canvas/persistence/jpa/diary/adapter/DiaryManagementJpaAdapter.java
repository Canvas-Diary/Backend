package com.canvas.persistence.jpa.diary.adapter;

import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.persistence.jpa.diary.DiaryMapper;
import com.canvas.persistence.jpa.diary.entity.DiaryEntity;
import com.canvas.persistence.jpa.diary.repository.DiaryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryManagementJpaAdapter implements DiaryManagementPort {

    private final DiaryJpaRepository diaryJpaRepository;

    @Override
    public Diary save(Diary diary) {
        DiaryEntity diaryEntity = diaryJpaRepository.save(DiaryMapper.toEntity(diary));
        return DiaryMapper.toDomain(diaryEntity);
    }

    @Override
    public Diary getById(Long diaryId) {
        DiaryEntity diaryEntity = diaryJpaRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 일기"));
        return DiaryMapper.toDomain(diaryEntity);
    }

    @Override
    public List<Diary> getByUserIdAndMonth(Long userId, LocalDate date) {
        LocalDateTime start = date.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime end = date.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);

        return diaryJpaRepository.findByWriterIdAndCreatedAtBetween(userId, start, end).stream()
                .map(DiaryMapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Diary diary) {
        diaryJpaRepository.deleteById(diary.getId());
    }

}
