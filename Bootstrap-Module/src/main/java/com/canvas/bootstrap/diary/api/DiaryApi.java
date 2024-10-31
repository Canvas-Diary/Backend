package com.canvas.bootstrap.diary.api;

import com.canvas.application.common.enums.Style;
import com.canvas.bootstrap.common.annotation.AccessUser;
import com.canvas.bootstrap.diary.dto.*;
import com.canvas.bootstrap.diary.enums.ExploreOrder;
import com.canvas.bootstrap.diary.enums.SearchType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Diary", description = "Diary API")
@RequestMapping("/api/v1/diaries")
public interface DiaryApi {

    @Operation(summary = "일기 생성")
    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 생성 성공",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CreateDiaryResponse.class)
                            )
                    }
            )
    })
    CreateDiaryResponse createDiary(@AccessUser String userId, @RequestBody CreateDiaryRequest createDiaryRequest);


    @Operation(summary = "내 일기 단건 조회")
    @GetMapping("/{diaryId}/my")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "내 일기 단건 조회 성공",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReadMyDiaryResponse.class)
                            )
                    }
            )
    })
    ReadMyDiaryResponse readMyDiary(@AccessUser String userId, @PathVariable String diaryId);

    @Operation(summary = "타인 일기 단건 조회")
    @GetMapping("/{diaryId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "타인 일기 단건 조회 성공",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReadOtherDiaryResponse.class)
                            )
                    }
            )
    })
    ReadOtherDiaryResponse readOtherDiary(@AccessUser String userId, @PathVariable String diaryId);


    @Operation(summary = "일기 달력 조회")
    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 달력별로 조회 성공",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ReadDiaryCalenderResponse.class)
                            )
                    }
            )
    })
    ReadDiaryCalenderResponse readDiaryCalender(@AccessUser String userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM") LocalDate date);


    @Operation(summary = "일기 내용 수정")
    @PatchMapping("/{diaryId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 내용 수정 성공"
            )
    })
    void updateDiary(@PathVariable String diaryId, @RequestBody UpdateDiaryRequest updateDiaryRequest);

    @Operation(summary = "일기 삭제")
    @DeleteMapping("/{diaryId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 삭제 성공"
            )
    })
    void deleteDiary(@PathVariable String diaryId);

    @Operation(summary = "일기 검색")
    @GetMapping("/search")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 검색 성공",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DiarySearchResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "쿼리 스트링 오류"
            )
    })
    DiarySearchResponse searchDiary(@RequestParam SearchType type, @RequestParam Style value);

    @Operation(summary = "상대방 일기 탐색")
    @GetMapping("/explore")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 탐색 성공",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DiaryExploreResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "쿼리 스트링 오류"
            )
    })
    DiaryExploreResponse exploreDiary(@RequestParam ExploreOrder order);
}
