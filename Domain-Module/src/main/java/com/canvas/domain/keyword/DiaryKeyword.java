package com.canvas.domain.keyword;

import com.canvas.domain.common.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DiaryKeyword {

    DomainId id;
    DomainId diaryId;
    DomainId keywordId;
}
