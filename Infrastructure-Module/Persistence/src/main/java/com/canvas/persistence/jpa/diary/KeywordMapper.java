package com.canvas.persistence.jpa.diary;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.keyword.Keyword;
import com.canvas.persistence.jpa.diary.entity.KeywordEntity;

public class KeywordMapper {
    public static KeywordEntity toEntity(Keyword keyword) {
        return new KeywordEntity(
                keyword.getId().value(),
                keyword.getKeyword(),
                DiaryKeywordMapper.toEntities(keyword)
        );
    }

    public static Keyword toDomain(KeywordEntity keywordEntity) {
        return new Keyword(
                DomainId.from(keywordEntity.getId()),
                keywordEntity.getName(),
                keywordEntity.getDiaryKeywordEntities().stream()
                        .map(DiaryKeywordMapper::toDomain)
                        .toList()
        );
    }

}
