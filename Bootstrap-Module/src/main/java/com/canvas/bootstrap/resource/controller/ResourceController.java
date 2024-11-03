package com.canvas.bootstrap.resource.controller;

import com.canvas.application.common.enums.Style;
import com.canvas.bootstrap.resource.api.ResourceApi;
import com.canvas.bootstrap.resource.dto.EmotionResponse;
import com.canvas.bootstrap.resource.dto.StyleResponse;
import com.canvas.domain.diary.enums.Emotion;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController implements ResourceApi {

    @Override
    public EmotionResponse getEmotions() {
        return EmotionResponse.from(Emotion.getAllEmotionInfo());
    }

    @Override
    public StyleResponse getStyles() {
        return StyleResponse.from(Style.getAllStyleInfo());
    }
}
