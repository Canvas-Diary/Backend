package com.canvas.domain.diary.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Emotion {

    ANGER("anger"),
    FEAR("fear"),
    HAPPINESS("happiness"),
    SADNESS("sadness"),
    SURPRISE("surprise"),
    INTEREST("interest"),
    DISGUST("disgust"),
    SHAME("shame"),
    NONE("no emotion");

    private final String name;


}
