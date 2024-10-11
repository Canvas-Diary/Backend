package com.canvas.domain.diary.vo;

import com.canvas.domain.diary.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DiaryContent {
    private String content;
    private List<Image> images;
}
