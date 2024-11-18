package com.canvas.bootstrap.keyword.api;

import com.canvas.bootstrap.common.annotation.AccessUser;
import com.canvas.bootstrap.stats.dto.KeywordStatsResponse;
import com.canvas.bootstrap.keyword.dto.SaveKeywordRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Tag(name = "Diary", description = "Diary API")
@RequestMapping("/api/v1/diaries")
public interface KeywordApi {

    @Operation(summary = "키워드 저장")
    @PostMapping("/{diaryId}/reminiscence")
    void saveKeyword(
            @AccessUser String userId,
            @PathVariable String diaryId,
            @RequestBody SaveKeywordRequest request
    );

}
