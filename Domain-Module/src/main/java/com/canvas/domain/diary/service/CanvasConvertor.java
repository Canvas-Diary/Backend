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
        WATERCOLOR("수채화 스타일의 이미지"),
        ACRYLICPAINTING("아크릴 스타일 이미지"),
        PENANDINK("펜과 잉크를 사용한 이미지"),
        PENCILDRAWING("연필을 사용한 이미지"),
        CHARCOALDRAWING("숯을 이용한 스타일의 이미지"),
        DIGITALART("다지털 아트"),
        COMICSTYLE("만화 스타일"),
        ANIMATIONSTYLE("애니메이션 스타일"),
        COLLAGE("콜라주");

        private final String name;
    }

    @RequiredArgsConstructor
    enum Format {
        ILLUSTRATION("일러스트 형식"),
        FOURPANELCOMIC("4컷 만화 형식"),
        POSTER("포스터 형식"),
        STORYBOARD("스토리 형식");

        private final String name;

    }

}
