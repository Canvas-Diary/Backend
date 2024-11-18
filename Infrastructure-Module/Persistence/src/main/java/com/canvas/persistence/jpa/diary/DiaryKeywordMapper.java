package com.canvas.persistence.jpa.diary;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.keyword.DiaryKeyword;
import com.canvas.domain.keyword.Keyword;
import com.canvas.persistence.jpa.diary.entity.DiaryKeywordEntity;

import java.util.List;

public class DiaryKeywordMapper {

    public static DiaryKeywordEntity toEntity(DiaryKeyword diaryKeyword) {
        return new DiaryKeywordEntity(
                diaryKeyword.getId().value(),
                diaryKeyword.getDiaryId().value(),
                diaryKeyword.getKeywordId().value()
        );
    }

    public static List<DiaryKeywordEntity> toEntities(Keyword keyword) {
        return keyword.getDiaryKeywords().stream()
                .map(DiaryKeywordMapper::toEntity)
                .toList();
    }


    public static DiaryKeyword toDomain(DiaryKeywordEntity diaryKeywordEntity) {
        return new DiaryKeyword(
          DomainId.from(diaryKeywordEntity.getId()),
          DomainId.from(diaryKeywordEntity.getDiaryId()),
          DomainId.from(diaryKeywordEntity.getKeywordId())
        );
    }

}
