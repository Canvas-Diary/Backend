package com.canvas.application.image.port.out;

import com.canvas.domain.common.DomainId;

public interface ImageDailyLimitPort {
    boolean isExceed(DomainId userId);
    void decrease(DomainId userId);
    void increase(DomainId userId);
    void reset(DomainId userId);
}
