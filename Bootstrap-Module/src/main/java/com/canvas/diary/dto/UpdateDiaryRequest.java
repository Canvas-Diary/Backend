package com.canvas.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateDiaryRequest {
    private final String content;
    private final boolean isPublic;
}
