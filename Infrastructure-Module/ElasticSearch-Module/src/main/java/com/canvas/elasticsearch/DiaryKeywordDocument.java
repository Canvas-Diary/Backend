package com.canvas.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@AllArgsConstructor
@Document(indexName = "diary_keyword")
public class DiaryKeywordDocument {

    @Id
    private Long id;
    private Long diaryId;
    private List<String> keywords;
}
