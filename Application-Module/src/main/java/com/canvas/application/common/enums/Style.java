package com.canvas.application.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum Style {
    PHOTOREALISTIC("photorealistic", "실사화", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%89%E1%85%B5%E1%86%AF%E1%84%89%E1%85%A1%E1%84%92%E1%85%AA.webp"),
    OILPAINTING("oil painting", "유화", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%8B%E1%85%B2%E1%84%92%E1%85%AA.webp"),
    WATERCOLOR("watercolor", "수채화", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%89%E1%85%AE%E1%84%8E%E1%85%A2%E1%84%92%E1%85%AA.webp"),
    ANIMATION("3D animation", "3D 그래픽", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%8B%E1%85%A2%E1%84%82%E1%85%B5%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%89%E1%85%A7%E1%86%AB+.webp"),
    PIXELART("8-bit graphics", "픽셀 아트", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%91%E1%85%B5%E1%86%A8%E1%84%89%E1%85%A6%E1%86%AF+%E1%84%8B%E1%85%A1%E1%84%90%E1%85%B3.webp"),
    COMICS("American comics style", "코믹스", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%8F%E1%85%A9%E1%84%86%E1%85%B5%E1%86%A8%E1%84%89%E1%85%B3.jpeg"),
    ABSTRACT("abstract", "추상화", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%8E%E1%85%AE%E1%84%89%E1%85%A1%E1%86%BC%E1%84%92%E1%85%AA.jpeg"),
    ANIME("Japanese animation style", "애니메이션", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%8B%E1%85%A2%E1%84%82%E1%85%B5%E1%84%86%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%89%E1%85%A7%E1%86%AB.jpeg"),
    LEGO("LEGO", "레고", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%85%E1%85%A6%E1%84%80%E1%85%A9.png"),
    POLYART("poly art, poly background", "폴리곤", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%91%E1%85%A9%E1%86%AF%E1%84%85%E1%85%B5%E1%84%80%E1%85%A9%E1%86%AB.jpg"),
    PAPER("layered paper style", "종이 입체", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B5.png"),
    IMPRESSIONISM("impressionism", "인상주의", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%8B%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A1%E1%86%BC%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B4.png"),
    EXPRESSIONISM("expressionism", "표현주의", "https://canvas-diary.s3.ap-northeast-2.amazonaws.com/%ED%99%94%ED%92%8D/%E1%84%91%E1%85%AD%E1%84%92%E1%85%A7%E1%86%AB%E1%84%8C%E1%85%AE%E1%84%8B%E1%85%B4.png");

    private final String value;
    private final String koreanName;
    private final String imageUrl;

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
            String koreanName,
            String imageUrl
    ) {
        public static StyleInfo create(Style style) {
            return new StyleInfo(style.name(), style.getKoreanName(), style.getImageUrl());
        }
    }

}
