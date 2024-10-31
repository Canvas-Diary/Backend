package com.canvas.bootstrap.diary.dto;

import com.canvas.domain.diary.enums.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "달력 조회 요청")
public record ReadDiaryCalenderResponse(
        @Schema(description = "달력에 필요한 일기 정보 리스트")
        List<CalenderInfo> diaries
) {
    @Schema(description = "달력에 필요한 일기 정보")
    public record CalenderInfo(
            @Schema(description = "일기 ID")
            String diaryId,
            @Schema(description = "일기 날짜")
            LocalDate date,
            @Schema(description = "일기의 감정")
            Emotion emotion
    ) {
    }
}

