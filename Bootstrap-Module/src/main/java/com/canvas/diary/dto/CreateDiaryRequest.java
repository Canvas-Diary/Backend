package com.canvas.diary.dto;

import com.canvas.application.common.enums.Style;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateDiaryRequest {
    private final LocalDateTime date;
    private final String content;
    private final Style style;
    private final boolean isPublic;
}
