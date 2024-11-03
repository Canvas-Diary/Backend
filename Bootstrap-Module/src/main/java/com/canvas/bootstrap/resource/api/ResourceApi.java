package com.canvas.bootstrap.resource.api;

import com.canvas.bootstrap.resource.dto.EmotionResponse;
import com.canvas.bootstrap.resource.dto.StyleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Resource", description = "Resource API")
@RequestMapping("/api/resources")
public interface ResourceApi {
    @Operation(summary = "감정 정보 조회")
    @GetMapping("/emotions")
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "감정 정보 조회 성공"
            )
    )
    EmotionResponse getEmotions();

    @Operation(summary = "화풍 정보 조회")
    @GetMapping("/styles")
    @ApiResponses(
            @ApiResponse(
                    responseCode = "200",
                    description = "화풍 정보 조회 성공"
            )
    )
    StyleResponse getStyles();
}
