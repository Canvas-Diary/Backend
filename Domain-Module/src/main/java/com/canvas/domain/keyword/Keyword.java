package com.canvas.domain.keyword;

import com.canvas.domain.common.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Keyword {
    private DomainId Id;
    private String keyword;
    private List<DiaryKeyword> diaryKeywords;
}
