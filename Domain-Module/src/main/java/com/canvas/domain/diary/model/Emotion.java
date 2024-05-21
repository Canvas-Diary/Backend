package com.canvas.domain.diary.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Emotion {

    ANGER("분노"),
    FEAR("두려움"),
    HAPPINESS("행복"),
    SADNESS("슬픔"),
    SURPRISE("놀라움"),
    INTEREST("관심"),
    DISGUST("혐오"),
    SHAME("수치심");

    private final String name;

}
