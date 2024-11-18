package com.canvas.openai.gpt;

import com.canvas.application.diary.port.out.DiaryEmotionExtractPort;
import com.canvas.application.diary.port.out.DiaryKeywordExtractPort;
import com.canvas.application.image.port.out.ImagePromptGeneratePort;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.openai.gpt.service.GptPromptConsts;
import com.canvas.openai.gpt.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@Primary
public class DiaryContentConvertGptAdapter
        implements DiaryEmotionExtractPort, ImagePromptGeneratePort, DiaryKeywordExtractPort {

    private final GptService gptService;

    @Override
    public Emotion emotionExtract(String content) {
        String emotion = gptService.generate(GptPromptConsts.EMOTION_EXTRACT + content);
        return Emotion.parse(emotion);
    }

    @Override
    public String generatePrompt(String content) {
        return gptService.generate(GptPromptConsts.IMAGE_GENERATOR + content);
    }

    @Override
    public List<String> keywordExtract(String content) {
        String response = gptService.generate(GptPromptConsts.KEYWORD_EXTRACT + content);
        return Arrays.stream(response.split(", ")).toList();
    }
}
