package com.canvas.domain.diary.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Emotion {
    ANGER("분노"),
    SADNESS("슬픔"),
    JOY("기쁨"),
    FEAR("공포"),
    DISGUST("혐오"),
    SHAME("부끄러움"),
    SURPRISE("놀라움"),
    CURIOSITY("호기심"),
    NONE("무분류");

    private final String koreanName;

    public static Emotion parse(String value) {
        return Arrays.stream(Emotion.values())
                .filter(e -> e.name().equals(value))
                .findFirst()
                     // TODO - 사용자 예외 처리로 변환 필요
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 감정"));
    }

    public static List<EmotionInfo> getAllEmotionInfo() {
        return Arrays.stream(values())
                .map(EmotionInfo::create)
                .toList();
    }

    public record EmotionInfo(
            String name,
            String koreanName
    ) {
        public static EmotionInfo create(Emotion emotion) {
            return new EmotionInfo(emotion.name(), emotion.getKoreanName());
        }
    }
}
