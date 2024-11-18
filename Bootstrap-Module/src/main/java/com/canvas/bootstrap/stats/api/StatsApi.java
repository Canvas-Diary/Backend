package com.canvas.bootstrap.stats.api;

import com.canvas.bootstrap.common.annotation.AccessUser;
import com.canvas.bootstrap.stats.dto.EmotionStatsResponse;
import com.canvas.bootstrap.stats.dto.KeywordStatsResponse;
import com.canvas.bootstrap.stats.type.StatsType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "Stats", description = "Statistics API")
@RequestMapping("/api/v1/stats")
public interface StatsApi {
    @Operation(summary = "감정 통계 조회")
    @GetMapping("/emotions")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "감정 통계 조회 성공"
            )
    })
    EmotionStatsResponse getEmotionStats(@AccessUser String userId, @RequestParam StatsType type, @RequestParam LocalDate date);

    @Operation(summary = "키워드 통계 조회")
    @GetMapping("/keywords")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "키워드 통계 조회 성공"
            )
    })
    KeywordStatsResponse getKeywordStats(
            @AccessUser String userId,
            @RequestParam StatsType type,
            @RequestParam LocalDate date
    );
}
