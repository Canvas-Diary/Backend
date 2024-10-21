package com.canvas.domain.diary.vo;

import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.enums.Emotion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DiaryContent {
    private String content;
    private Emotion emotion;
    private List<Image> images = new ArrayList<>();

    public DiaryContent(String content, Emotion emotion, List<Image> images) {
        this.content = content;
        this.emotion = emotion;
        this.images.addAll(images);
    }

    public void addImage(Image image) {
        this.images.add(image);
    }
}
