package com.canvas.google.gemini.service;

public class GeminiPromptConsts {
    public static final String IMAGE_GENERATOR = """
            Choose only one of the most important scenes in the following content, and make a sentence that specifically describes the main scene suitable for the prompt in the image Generative AI. If the content is inappropriate, change it to a very mild direction. Don't print the title, just print the contents of the prompt in English.
            
            """;

    public static final String EMOTION_EXTRACT = """
            Choose one of ANGER, SADNESS, JOY, FEAR, DISGUST, SHAME, SURPRISE, CURIOSITY, and print out the main emotions in the following diary contents in just one word. If the emotions are not clearly visible or listed, print NONE.
            
            """;

    public static final String KEYWORD_EXTRACT = """
            일기에서 가장 핵심적인 키워드를 1~5개를 뽑아줘. 정확히 이 형식에 맞춰 뽑아줘 "keyword1, keyword2, keyword3, keyword4, keyword5"
            """;
}
