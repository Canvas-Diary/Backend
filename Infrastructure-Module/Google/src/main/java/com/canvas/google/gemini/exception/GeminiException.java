package com.canvas.google.gemini.exception;

// TODO - 공통 커스텀 예외로 리팩토링 필요
public class GeminiException extends IllegalArgumentException {

    public GeminiException(String s) {
        super(s);
    }

    public static class GeminiSafetyException extends GeminiException {
        public GeminiSafetyException() {
            super("유해한 프롬프트");
        }

        public GeminiSafetyException(String s) {
            super(s);
        }
    }
}
