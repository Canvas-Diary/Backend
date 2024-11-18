package com.canvas.domain.diary.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.canvas.domain.diary.enums.Sentiment.*;

@RequiredArgsConstructor
@Getter
public enum Emotion {
    ANGER("분노", NEGATIVE),
    SADNESS("슬픔", NEGATIVE),
    JOY("기쁨", POSITIVE),
    FEAR("공포", NEGATIVE),
    DISGUST("혐오", NEGATIVE),
    SHAME("부끄러움", NEGATIVE),
    SURPRISE("놀라움", NEUTRAL),
    CURIOSITY("호기심", POSITIVE),
    NONE("무분류", NEUTRAL);

    private final String koreanName;
    private final Sentiment sentiment;

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
