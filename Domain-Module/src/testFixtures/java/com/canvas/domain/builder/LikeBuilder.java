package com.canvas.domain.builder;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Like;

public class LikeBuilder {
    private DomainId id;
    private DomainId userId;
    private DomainId diaryId;

    public LikeBuilder id(DomainId id) {
        this.id = id;
        return this;
    }

    public LikeBuilder userId(DomainId userId) {
        this.userId = userId;
        return this;
    }

    public LikeBuilder diaryId(DomainId diaryId) {
        this.diaryId = diaryId;
        return this;
    }

    public Like build() {
        return Like.create(id, userId, diaryId);
    }
}
