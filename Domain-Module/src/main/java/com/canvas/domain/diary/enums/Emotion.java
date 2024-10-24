package com.canvas.domain.diary.enums;

import java.util.Arrays;

public enum Emotion {
    ANGER, SADNESS, JOY, FEAR, DISGUST, SHAME, SURPRISE, CURIOSITY, NONE;

    public static Emotion parse(String value) {
        return Arrays.stream(Emotion.values())
                .filter(e -> e.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 감정"));
    }
}
