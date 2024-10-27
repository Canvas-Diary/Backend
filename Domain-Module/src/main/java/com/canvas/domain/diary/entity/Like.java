package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Like {
    private DomainId id;
    private DomainId userId;
    private DomainId diaryId;

    public static Like create(DomainId id, DomainId userId, DomainId diaryId) {
        return new Like(id, userId, diaryId);
    }
}
