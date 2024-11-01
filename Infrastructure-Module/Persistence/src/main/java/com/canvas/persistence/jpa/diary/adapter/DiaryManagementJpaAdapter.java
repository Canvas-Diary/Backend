package com.canvas.persistence.jpa.diary.adapter;

import com.canvas.application.diary.exception.DiaryException;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.common.page.PageRequest;
import com.canvas.common.page.Slice;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.persistence.jpa.common.PageMapper;
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
    public Diary getPublicById(DomainId diaryId) {
        DiaryEntity diaryEntity = diaryJpaRepository.findByIdAndIsPublicTrue(diaryId.value())
                .orElseThrow(DiaryException.DiaryNotFoundException::new);
        return DiaryMapper.toDomain(diaryEntity);
    }

    @Override
    public Diary getByIdAndWriterId(DomainId diaryId, DomainId writerId) {
        DiaryEntity diaryEntity = diaryJpaRepository.findByIdAndWriterId(diaryId.value(), writerId.value())
                .orElseThrow(DiaryException.DiaryNotFoundException::new);
        return DiaryMapper.toDomain(diaryEntity);
    }

    @Override
    public boolean existsByIdAndWriterId(DomainId diaryId, DomainId writerId) {
        return diaryJpaRepository.existsByIdAndWriterId(diaryId.value(), writerId.value());
    }

    @Override
    public List<Diary> getByUserIdAndMonth(DomainId userId, LocalDate date) {
        LocalDateTime start = date.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();
        LocalDateTime end = date.with(TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);

        return diaryJpaRepository.findByWriterIdAndDateTimeBetween(userId.value(), start, end).stream()
                .map(DiaryMapper::toDomain)
                .toList();
    }

    @Override
    public Slice<Diary> getAlbum(PageRequest pageRequest, DomainId userId) {
        var diaryEntities = diaryJpaRepository.findByWriterId(
                PageMapper.toJpaPageRequest(pageRequest),
                userId.value()
        );

        return PageMapper.toDomainSlice(diaryEntities, DiaryMapper::toDomain);
    }

    @Override
    public Slice<Diary> getAlbumByContent(PageRequest pageRequest, DomainId userId, String content) {
        var diaryEntities = diaryJpaRepository.findByWriterIdAndContentContains(
                PageMapper.toJpaPageRequest(pageRequest),
                userId.value(),
                content
        );

        return PageMapper.toDomainSlice(diaryEntities, DiaryMapper::toDomain);
    }

    @Override
    public Slice<Diary> getAlbumByEmotion(PageRequest pageRequest, DomainId userId, Emotion emotion) {
        var diaryEntities = diaryJpaRepository.findByWriterIdAndEmotion(
                PageMapper.toJpaPageRequest(pageRequest),
                userId.value(),
                emotion.name()
        );

        return PageMapper.toDomainSlice(diaryEntities, DiaryMapper::toDomain);
    }

    @Override
    public Slice<Diary> getExploreByLatest(PageRequest pageRequest) {
        var diaryEntities = diaryJpaRepository.findAllByIsPublicTrue(
                PageMapper.toJpaPageRequest(pageRequest)
        );

        return PageMapper.toDomainSlice(diaryEntities, DiaryMapper::toDomain);
    }

    @Override
    public Slice<Diary> getExploreByLike(PageRequest pageRequest) {
        var diaryEntities = diaryJpaRepository.findAllByIsPublicTrueOrderByLikeCountDesc(
                PageMapper.toJpaPageRequest(pageRequest)
        );

        return PageMapper.toDomainSlice(diaryEntities, DiaryMapper::toDomain);
    }

    @Override
    public void deleteById(DomainId diaryId) {
        diaryJpaRepository.deleteById(diaryId.value());
    }

}
