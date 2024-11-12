package com.canvas.google.gemini.service;

public class GeminiPromptConsts {
    public static final String IMAGE_GENERATOR = """
            Choose only one of the most important scenes in the following content, and make a sentence that specifically describes the main scene suitable for the prompt in the image Generative AI. If the contents are inappropriate, print FORBIDDEN. Don't print the title, just print the contents of the prompt in English.
            
            """;

    public static final String EMOTION_EXTRACT = """
            Choose one of ANGER, SADNESS, JOY, FEAR, DISGUST, SHAME, SURPRISE, CURIOSITY, and print out the main emotions in the following diary contents in just one word. If the contents are inappropriate, print FORBIDDEN, and if the emotions are not clearly visible or listed, print NONE.
            
            """;
}
