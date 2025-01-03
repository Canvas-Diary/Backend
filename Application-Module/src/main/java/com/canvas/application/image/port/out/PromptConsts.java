package com.canvas.application.image.port.out;

public class PromptConsts {
    public static final String IMAGE_GENERATOR = """
            Choose only one of the most important scenes in the following content, and make a sentence that specifically describes the main scene suitable for the prompt in the image Generative AI. If the content is inappropriate, change it to a very mild direction. Don't print the title, just print the contents of the prompt in English.
            Content: %s ,
            Contents that should be repeated to highlight: %s
            """;

    public static final String EMOTION_EXTRACT = """
            Choose one of ANGER, SADNESS, JOY, FEAR, DISGUST, SHAME, SURPRISE, CURIOSITY, and print out the main emotions in the following diary contents in just one word. If the emotions are not clearly visible or listed, print NONE.
            
            """;

    public static final String KEYWORD_EXTRACT = """
            "일기 내용에서부터 가장 핵심적인 키워드를 반드시 1~5개를 뽑아줘.
            키워드를 뽑을 때 보편적인 단어로만 뽑아줘
            키워드를 뽑을 때 일기 내용에 있는 것에서만 뽑아줘. 키워드 추출은 반드시 5개 이하로 보내줘.
            너가 반환하는 값을 무조건 이 형식에 맞춰서 반환해줘 '키워드1, 키워드2, 키워드3, 키워드4, 키워드5'"
            일기 내용: %s
            """;
}
