package com.canvas.application.keyword.port.out;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.keyword.Keyword;

import java.util.List;

public interface KeywordManagementPort {
    void saveAllKeywords(List<Keyword> keywords);

    List<Keyword> findAllByNames(List<String> keywords);

    List<Keyword> findByKeywordsId(List<DomainId> ids);
}
