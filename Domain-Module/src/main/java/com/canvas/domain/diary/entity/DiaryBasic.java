package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DiaryBasic {
    // 조회 전용 도메인
    private DomainId id;
    private DomainId writerId;
    private String content;
    private Emotion emotion;
    private LocalDateTime dateTime;
    private LocalDateTime createdAt;
    private Boolean isPublic;

    public static DiaryBasic create(
            DomainId id,
            DomainId writerId,
            String content,
            Emotion emotion,
            LocalDateTime dateTime,
            LocalDateTime createdAt,
            Boolean isPublic
    ) {
        return new DiaryBasic(id, writerId, content, emotion, dateTime, createdAt, isPublic);
    }
}
