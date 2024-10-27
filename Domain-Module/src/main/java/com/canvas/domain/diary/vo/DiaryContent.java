package com.canvas.domain.diary.vo;

import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.enums.Emotion;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DiaryContent {
    private String content;
    private Emotion emotion;
    private List<Image> images;

    public static DiaryContent create(String content, Emotion emotion, List<Image> images) {
        return new DiaryContent(content, emotion, images);
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public String getMainImageOrDefault(){
        return images.stream()
                .filter(Image::isMain)
                .map(Image::getImageUrl)
                .findFirst()
                .orElse(Image.DEFAULT_IMAGE_URL);
    }
}
