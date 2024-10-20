package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Like {
    private DomainId id;
    private DomainId userId;
    private DomainId diaryId;
}
