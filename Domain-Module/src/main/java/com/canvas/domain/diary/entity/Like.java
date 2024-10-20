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

    public static Like withoutId(DomainId userId, DomainId diaryId) {
        return new Like(DomainId.generate(), userId, diaryId);
    }

    public static Like withId(DomainId domainId, DomainId userId, DomainId diaryId) {
        return new Like(domainId, userId, diaryId);
    }
}
