package com.canvas.application.diary.port.out;

import com.canvas.domain.diary.enums.Emotion;

public interface DiaryEmotionExtractPort {
    Emotion emotionExtract(String content);
}
