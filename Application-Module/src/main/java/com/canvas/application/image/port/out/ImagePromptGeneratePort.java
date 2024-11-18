package com.canvas.application.image.port.out;

public interface ImagePromptGeneratePort {
    String generatePrompt(String content, String weightedContent);
}
