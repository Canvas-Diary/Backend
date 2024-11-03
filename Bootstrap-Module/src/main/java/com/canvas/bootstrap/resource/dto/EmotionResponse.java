package com.canvas.bootstrap.resource.dto;

import com.canvas.domain.diary.enums.Emotion;

import java.util.List;

public record EmotionResponse(
        List<EmotionInfo> emotions
) {
    public static EmotionResponse from(List<Emotion.EmotionInfo> emotionInfos) {
        return new EmotionResponse(
                emotionInfos.stream()
                        .map(EmotionInfo::from)
                        .toList());
    }

    public record EmotionInfo(
            String name,
            String koreanName
    ) {
        public static EmotionInfo from(Emotion.EmotionInfo emotionInfo) {
            return new EmotionInfo(emotionInfo.name(), emotionInfo.koreanName());
        }
    }
}
