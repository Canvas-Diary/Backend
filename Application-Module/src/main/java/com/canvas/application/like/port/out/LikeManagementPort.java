package com.canvas.application.like.port.out;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Like;

public interface LikeManagementPort {
    void add(Like like);
    void remove(DomainId userId, DomainId diaryId);
}
