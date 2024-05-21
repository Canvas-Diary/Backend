package com.canvas.api.diary.application.dto;


import com.canvas.domain.diary.model.Emotion;

public class DiaryCreate{

    public record Request(
            String content,
            String imageUrl,
            Emotion emotion
    ) {
    }
}
