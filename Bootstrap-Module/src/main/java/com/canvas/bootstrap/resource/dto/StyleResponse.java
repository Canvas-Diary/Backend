package com.canvas.bootstrap.resource.dto;

import com.canvas.application.common.enums.Style;

import java.util.List;

public record StyleResponse(
        List<StyleInfo> styles
) {
    public static StyleResponse from(List<Style.StyleInfo> styleInfos) {
        return new StyleResponse(
                styleInfos.stream()
                        .map(StyleInfo::from)
                        .toList());
    }

    public record StyleInfo(
            String name,
            String koreanName,
            String imageUrl
    ) {
        public static StyleInfo from(Style.StyleInfo styleInfo) {
            return new StyleInfo(styleInfo.name(), styleInfo.koreanName(), styleInfo.imageUrl());
        }
    }
}
