package com.canvas.persistence.jpa.dailylimit.adapter;

import com.canvas.application.image.port.out.ImageDailyLimitPort;
import com.canvas.domain.common.DomainId;
import com.canvas.persistence.jpa.dailylimit.entity.DailyLimitEntity;
import com.canvas.persistence.jpa.dailylimit.repository.DailyLimitJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ImageDailyLimitJpaAdapter implements ImageDailyLimitPort {

    private static final int LIMIT = 5;

    private final DailyLimitJpaRepository dailyLimitJpaRepository;

    @Override
    public boolean isExceed(DomainId userId) {
        return dailyLimitJpaRepository.countByUserIdAndDate(userId.value(), LocalDate.now()) >= LIMIT;
    }

    @Override
    public void decrease(DomainId userId) {
        dailyLimitJpaRepository.save(new DailyLimitEntity(userId.value()));
    }

    @Override
    public void increase(DomainId userId) {
        dailyLimitJpaRepository.deleteDistinctByUserId(userId.value());
    }

    @Override
    public void reset(DomainId userId) {
        dailyLimitJpaRepository.deleteByUserId(userId.value());
    }

}
