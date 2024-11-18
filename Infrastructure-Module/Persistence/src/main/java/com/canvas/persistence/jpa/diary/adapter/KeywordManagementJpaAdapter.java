package com.canvas.persistence.jpa.diary.adapter;

import com.canvas.application.keyword.port.out.DiaryKeywordManagementPort;
import com.canvas.application.keyword.port.out.KeywordManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.keyword.DiaryKeyword;
import com.canvas.domain.keyword.Keyword;
import com.canvas.persistence.jpa.diary.DiaryKeywordMapper;
import com.canvas.persistence.jpa.diary.KeywordMapper;
import com.canvas.persistence.jpa.diary.entity.DiaryKeywordEntity;
import com.canvas.persistence.jpa.diary.entity.KeywordEntity;
import com.canvas.persistence.jpa.diary.repository.DiaryKeywordJpaRepository;
import com.canvas.persistence.jpa.diary.repository.KeywordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class KeywordManagementJpaAdapter implements KeywordManagementPort, DiaryKeywordManagementPort {

    private final KeywordJpaRepository keywordJpaRepository;
    private final DiaryKeywordJpaRepository diaryKeywordJpaRepository;

    @Override
    public List<Keyword> findAllByNames(List<String> names) {
        List<KeywordEntity> keywordEntities = keywordJpaRepository.findByKeywords(names);
        return keywordEntities.stream().map(KeywordMapper::toDomain).toList();
    }

    @Override
    public List<Keyword> findByKeywordsId(List<DomainId> keywordIdList) {
        List<UUID> keywordIds = keywordIdList.stream().map(DomainId::value).toList();
        return keywordJpaRepository.findByKeywordIds(keywordIds)
                .stream()
                .map(KeywordMapper::toDomain)
                .toList();
    }

    @Override
    public void saveAllKeywords(List<Keyword> keywords) {
        List<KeywordEntity> keywordEntities = keywords.stream().map(KeywordMapper::toEntity).toList();
        keywordJpaRepository.saveAll(keywordEntities);
    }

    @Override
    public void saveAllDiaryKeywords(List<DiaryKeyword> diaryKeywords) {
        List<DiaryKeywordEntity> diaryKeywordEntities = diaryKeywords.stream().map(DiaryKeywordMapper::toEntity).toList();
        diaryKeywordJpaRepository.saveAll(diaryKeywordEntities);
    }

    @Override
    public List<DiaryKeyword> findByWriteIdAndBetween(DomainId userId, LocalDate start, LocalDate end) {
        List<DiaryKeywordEntity> diaryKeywordEntities =
                diaryKeywordJpaRepository.getByWriteIdAndBetween(userId.value(), start, end);
        return diaryKeywordEntities.stream()
                .map(DiaryKeywordMapper::toDomain)
                .toList();
    }
}
