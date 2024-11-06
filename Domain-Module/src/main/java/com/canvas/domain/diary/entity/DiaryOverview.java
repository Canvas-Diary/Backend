package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.enums.Emotion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DiaryOverview {
    // 조회 전용 도메인
    private DomainId id;
    private DomainId writerId;
    private String content;
    private Emotion emotion;
    private LocalDate date;
    private LocalDateTime createdAt;
    private Boolean isPublic;
    private List<Image> images;

    public static DiaryOverview create(
            DomainId id,
            DomainId writerId,
            String content,
            Emotion emotion,
            LocalDate date,
            LocalDateTime createdAt,
            Boolean isPublic,
            List<Image> images
    ) {
        return new DiaryOverview(id, writerId, content, emotion, date, createdAt, isPublic, images);
    }

    public String getMainImageOrDefault() {
        return images.stream()
                     .filter(Image::isMain)
                     .map(Image::getImageUrl)
                     .findFirst()
                     .orElse(Image.DEFAULT_IMAGE_URL);
    }
}
