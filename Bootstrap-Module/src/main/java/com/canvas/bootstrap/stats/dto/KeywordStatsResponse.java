package com.canvas.bootstrap.stats.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "키워드 통계 조회 응답")
public record KeywordStatsResponse(
        @Schema(description = "키워드 통계 정보")
        List<StaticInfo> keywordData
) {

    public record StaticInfo (
            String name,
            Integer value
    ) {}
}
