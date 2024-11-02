package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DiaryBasic {
    // 조회 전용 도메인
    private DomainId id;
    private DomainId writerId;
    private String content;
    private Emotion emotion;
    private LocalDate date;
    private LocalDateTime createdAt;
    private Boolean isPublic;

    public static DiaryBasic create(
            DomainId id,
            DomainId writerId,
            String content,
            Emotion emotion,
            LocalDate date,
            LocalDateTime createdAt,
            Boolean isPublic
    ) {
        return new DiaryBasic(id, writerId, content, emotion, date, createdAt, isPublic);
    }
}
