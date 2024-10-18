package com.canvas.google.gemini.service;

public class GeminiPromptConsts {
    public static final String IMAGE_GENERATOR = """
            다음 일기 내용에서 유해하지 않은 가장 중요한 장면을 하나만 정하고, 그 주요 장면을 구체적으로 설명하는 문장을
            이미지 생성형 AI의 프롬프트에 적합하게 만들어. 제목은 출력하지 말고 그 프롬프트 내용만 영문으로 출력해.
            
            """;

    public static final String EMOTION_EXTRACT = """
            다음 일기 내용에서 가장 주된 감정을 ANGER, SADNESS, JOY, FEAR, DISGUST, SHAME, SURPRISE, CURIOSITY
            중 하나만 골라서 한 단어로만 출력해. 감정이 잘 드러나지 않거나 목록에 없다면 NONE을 출력해.
            
            """;
}
