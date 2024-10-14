package com.canvas.application.diary.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Style {
    PHOTOREALISTIC("photorealistic"),
    OILPAINTING("oil painting"),
    WATERCOLOR("watercolor"),
    ANIMATION("animation"),
    PIXELART("pixel art");

    private final String value;

    public static Style parse(String value) {
        return Arrays.stream(Style.values())
                .filter(s -> s.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 화풍"));
    }
}
