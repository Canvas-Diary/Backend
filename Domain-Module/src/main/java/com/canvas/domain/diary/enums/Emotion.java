package com.canvas.domain.diary.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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

    private final String value;

    public static Emotion parse(String value) {
        return Arrays.stream(Emotion.values())
                .filter(e -> e.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 감정"));
    }
}
