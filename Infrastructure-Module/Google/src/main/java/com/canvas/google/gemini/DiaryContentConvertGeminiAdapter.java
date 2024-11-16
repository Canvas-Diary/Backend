package com.canvas.google.gemini;

import com.canvas.application.diary.port.out.DiaryEmotionExtractPort;
import com.canvas.application.diary.port.out.DiaryKeywordExtractPort;
import com.canvas.application.image.port.out.ImagePromptGeneratePort;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.google.gemini.service.LLMPromptConsts;
import com.canvas.google.gemini.service.LLMService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiaryContentConvertGeminiAdapter
        implements DiaryEmotionExtractPort, ImagePromptGeneratePort, DiaryKeywordExtractPort {

    private final LLMService llmService;

    @Override
    public Emotion emotionExtract(String content) {
        String emotion = llmService.generate(LLMPromptConsts.EMOTION_EXTRACT + content);
        return Emotion.parse(emotion);
    }

    @Override
    public String generatePrompt(String content) {
        return llmService.generate(LLMPromptConsts.IMAGE_GENERATOR + content);
    }

    @Override
    public List<String> keywordExtract(String content) {
        String response = llmService.generate(LLMPromptConsts.KEYWORD_EXTRACT + content);
        return Arrays.stream(response.split(", ")).toList();
    }
}
