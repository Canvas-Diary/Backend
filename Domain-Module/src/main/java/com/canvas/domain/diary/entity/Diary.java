package com.canvas.domain.diary.entity;

import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.enums.Emotion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Diary {
    private DomainId id;
    private DomainId writerId;
    private String content;
    private Emotion emotion;
    private LocalDateTime dateTime;     // 일기 날짜
    private LocalDateTime createdAt;    // 일기가 생성된 실제 시각
    private Boolean isPublic;
    private List<Image> images;
    private List<Like> likes;

    public static Diary create(
            DomainId id,
            DomainId writerId,
            String content,
            Emotion emotion,
            LocalDateTime dateTime,
            LocalDateTime createdAt,
            Boolean isPublic,
            List<Image> images,
            List<Like> likes
    ) {
        return new Diary(
                id,
                writerId,
                content,
                emotion,
                dateTime,
                createdAt,
                isPublic,
                images,
                likes
        );
    }

    public static Diary create(
            DomainId id,
            DomainId writerId,
            String content,
            Emotion emotion,
            LocalDateTime dateTime,
            Boolean isPublic,
            Image image
    ) {
        return new Diary(
                id,
                writerId,
                content,
                emotion,
                dateTime,
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


    public String getMainImageOrDefault() {
        return images.stream()
                     .filter(Image::isMain)
                     .map(Image::getImageUrl)
                     .findFirst()
                     .orElse(Image.DEFAULT_IMAGE_URL);
    }
}
