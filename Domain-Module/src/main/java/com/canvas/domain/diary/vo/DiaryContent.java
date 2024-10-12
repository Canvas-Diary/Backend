package com.canvas.domain.diary.vo;

import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.enums.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DiaryContent {
    private final String content;
    private final List<Image> images;
    private final Emotion emotion;
}
