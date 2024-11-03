package com.canvas.application.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Style {
    PHOTOREALISTIC("photorealistic", "실사화"),
    OILPAINTING("oil painting", "유화"),
    WATERCOLOR("watercolor", "수채화"),
    ANIMATION("animation", "애니메이션"),
    PIXELART("pixel art", "픽셀 아트");

    private final String value;
    private final String koreanName;

    public static Style parse(String value) {
        return Arrays.stream(Style.values())
                .filter(s -> s.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 화풍"));
    }

    public static List<StyleInfo> getAllStyleInfo() {
        return Arrays.stream(values())
                .map(StyleInfo::create)
                .toList();
    }

    public record StyleInfo(
            String name,
            String koreanName
    ) {
        public static StyleInfo create(Style style) {
            return new StyleInfo(style.name(), style.getKoreanName());
        }
    }

}
