package com.canvas.client.google.gemini;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeminiPromptConsts {

    public static final String EMOTION_EXTRACT_PROMPT = """
            다음 일기 내용에서 주된 감정이 분노·두려움·행복·슬픔·관심·놀라움·혐오·수치심 중 무엇인지 하나만 알려줘.
            
            """;
}
