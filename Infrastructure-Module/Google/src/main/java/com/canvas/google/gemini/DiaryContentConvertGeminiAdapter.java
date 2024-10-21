package com.canvas.google.gemini;

import com.canvas.application.diary.port.out.DiaryEmotionExtractPort;
import com.canvas.application.image.port.out.ImagePromptGeneratePort;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.google.gemini.service.GeminiPromptConsts;
import com.canvas.google.gemini.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryContentConvertGeminiAdapter
        implements DiaryEmotionExtractPort, ImagePromptGeneratePort {

    private final GeminiService geminiService;

    @Override
    public Emotion emotionExtract(String content) {
        String emotion = geminiService.generate(GeminiPromptConsts.EMOTION_EXTRACT);
        return Emotion.parse(emotion);
    }

    @Override
    public String generatePrompt(String content) {
        return geminiService.generate(GeminiPromptConsts.IMAGE_GENERATOR + content);
    }
}
