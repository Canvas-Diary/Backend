package com.canvas.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface DiaryKeywordRepository extends ElasticsearchRepository<DiaryKeywordDocument, Long> {

}
