package com.canvas.domain.diary.service;

import com.canvas.domain.diary.model.Emotion;
import lombok.RequiredArgsConstructor;

import java.util.List;

public interface CanvasConvertor {
    List<String> convertDiaryToCanvas(CanvasConvertProcessingData data);

    record CanvasConvertProcessingData(
            String diaryDescription,
            Emotion emotion,
            Style style
    ) {
    }

    @RequiredArgsConstructor
    enum Style {
        PHOTO("실제 사진처럼 보이는 이미지"),
        OILPAINTING("기름물감 그림 스타일의 이미지"),
        WATERCOLORPAINTING("수채화 스타일의 이미지"),
        ILLUSTRATION("일반적인 그림 또는 일러스트 스타일"),
        CARTOON("만화 스타일"),
        DRAWING("손그림 스타일"),
        VECTOR("벡터 그래픽 스타일"),
        RENDER("3D 렌더링 스타일");

        private final String name;
    }

}
