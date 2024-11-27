package com.canvas.persistence.jpa.dailylimit.entity;

import com.canvas.domain.common.DomainId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "daily_limit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DailyLimitEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;
    private LocalDate date;

    public DailyLimitEntity(UUID userId) {
        this.id = DomainId.generate().value();
        this.userId = userId;
        this.date = LocalDate.now();
    }

}
