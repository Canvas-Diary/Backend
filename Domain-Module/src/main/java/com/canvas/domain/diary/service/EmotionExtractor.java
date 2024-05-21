package com.canvas.domain.diary.service;

import java.util.concurrent.CompletableFuture;

public interface EmotionExtractor {
    String extractEmotion(EmotionExtractProcessingData data);


    CompletableFuture<String> extractEmotionAsync(EmotionExtractProcessingData data);

    record EmotionExtractProcessingData(
            String diaryDescription
    ) {
    }
}
