package com.canvas.diary.dto;

import com.canvas.domain.diary.enums.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "달력 조회 요청")
@Getter
@AllArgsConstructor
public class ReadDiaryCalenderResponse {

    private final List<CalenderInfo> diaries;

    @Getter
    @AllArgsConstructor
    class CalenderInfo {
        private final String diaryId;
        private final LocalDateTime date;
        private final Emotion emotion;
    }

}

