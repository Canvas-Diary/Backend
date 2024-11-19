package com.canvas.openai.gpt;

import com.canvas.application.diary.port.out.DiaryEmotionExtractPort;
import com.canvas.application.diary.port.out.DiaryKeywordExtractPort;
import com.canvas.application.image.port.out.ImagePromptGeneratePort;
import com.canvas.application.image.port.out.PromptConsts;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.openai.gpt.service.GptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Primary
@Slf4j
@Component
@RequiredArgsConstructor
public class DiaryContentConvertGptAdapter
        implements DiaryEmotionExtractPort, ImagePromptGeneratePort, DiaryKeywordExtractPort {

    private final GptService gptService;

    @Override
    public Emotion emotionExtract(String content) {
        String emotion = gptService.generate(PromptConsts.EMOTION_EXTRACT + content);
        return Emotion.parse(emotion);
    }

    @Override
    public String generatePrompt(String content, String joinedWeightedContents) {
        return gptService.generate(String.format(PromptConsts.IMAGE_GENERATOR, content, joinedWeightedContents));
    }

    @Override
    public List<String> keywordExtract(String content) {
        String response = gptService.generate(String.format(PromptConsts.KEYWORD_EXTRACT, content));
        return Arrays.stream(response.split(", ")).toList();
    }
}
