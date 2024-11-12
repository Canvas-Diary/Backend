package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "과거 일기 조히 요청")
public record ReminiscenceRequest(
        @Schema(description = "일기 내용")
        String content,
        @Schema(description = "일기 날짜")
        LocalDate date
) {
}
