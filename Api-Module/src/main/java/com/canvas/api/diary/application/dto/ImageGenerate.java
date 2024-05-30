package com.canvas.api.diary.application.dto;


import com.canvas.domain.diary.model.Emotion;
import com.canvas.domain.diary.service.CanvasConvertor;

import java.util.List;

public class ImageGenerate {
    public record Request(
            String description,
            Emotion emotion,
            CanvasConvertor.Style style,
            CanvasConvertor.Format format
    ) {
    }

    public record Response(
            List<String> canvasImageUrl
    ){

    }

    public static Response convertToGeneratedImageToResponse(List<String> canvasImageUrl) {
        return new Response(canvasImageUrl);
    }
}
