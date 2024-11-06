package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.enums.Emotion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DiaryComplete {
    private DomainId id;
    private DomainId writerId;
    private String content;
    private Emotion emotion;
    private LocalDate date;     // 일기 날짜
    private LocalDateTime createdAt;    // 일기가 생성된 실제 시각
    private Boolean isPublic;
    private List<Image> images;
    private List<Like> likes;

    public static DiaryComplete create(
            DomainId id,
            DomainId writerId,
            String content,
            Emotion emotion,
            LocalDate date,
            LocalDateTime createdAt,
            Boolean isPublic,
            List<Image> images,
            List<Like> likes
    ) {
        return new DiaryComplete(
                id,
                writerId,
                content,
                emotion,
                date,
                createdAt,
                isPublic,
                images,
                likes
        );
    }

    public static DiaryComplete create(
            DomainId id,
            DomainId writerId,
            String content,
            Emotion emotion,
            LocalDate date,
            Boolean isPublic,
            Image image
    ) {
        return new DiaryComplete(
                id,
                writerId,
                content,
                emotion,
                date,
                null,
                isPublic,
                new ArrayList<>(List.of(image)),
                new ArrayList<>()
        );
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public void addLike(Like like) {
        this.likes.add(like);
    }

    public void updateDiaryContent(String content, Emotion emotion, Image image) {
        this.content = content;
        this.emotion = emotion;
        this.images = new ArrayList<>(List.of(image));
    }

    public void updatePublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Integer getLikeCount() {
        return likes.size();
    }

    public boolean isLiked(DomainId userId) {
        return likes.stream()
                .anyMatch(like -> like.getUserId().equals(userId));
    }
}
