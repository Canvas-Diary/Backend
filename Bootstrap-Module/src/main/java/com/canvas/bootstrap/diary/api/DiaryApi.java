package com.canvas.bootstrap.diary.api;

import com.canvas.bootstrap.common.annotation.AccessUser;
import com.canvas.bootstrap.diary.dto.*;
import com.canvas.bootstrap.diary.enums.ExploreOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
                    description = "일기 생성 성공"
            )
    })
    CreateDiaryResponse createDiary(@AccessUser String userId, @RequestBody CreateDiaryRequest createDiaryRequest);


    @Operation(summary = "일기 단건 조회")
    @GetMapping("/{diaryId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 단건 조회 성공"
            )
    })
    ReadDiaryResponse readDiary(@AccessUser String userId, @PathVariable String diaryId);

    @Operation(summary = "일기 달력 조회")
    @GetMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 달력별로 조회 성공"
            )
    })
    ReadDiaryCalenderResponse readDiaryCalender(@AccessUser String userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM") LocalDate date);


    @Operation(summary = "일기 내용 수정")
    @PutMapping("/{diaryId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 내용 수정 성공"
            )
    })
    void updateDiary(@AccessUser String userId, @PathVariable String diaryId, @RequestBody UpdateDiaryRequest updateDiaryRequest);

    @Operation(summary = "일기 삭제")
    @DeleteMapping("/{diaryId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 삭제 성공"
            )
    })
    void deleteDiary(@AccessUser String userId, @PathVariable String diaryId);

    @Operation(summary = "일기 검색")
    @GetMapping("/search")
    @Parameters({
            @Parameter(
                    name = "page",
                    description = "요청할 페이지 번호"
            ),
            @Parameter(
                    name = "size",
                    description = "요청할 페이지 크기"
            ),
            @Parameter(
                    name = "type",
                    description = "검색 타입"
            ),
            @Parameter(
                    name = "value",
                    description = "검색 값"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 검색 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "쿼리 스트링 오류"
            )
    })
    SliceResponse<DiaryThumbnail> searchDiary(
            @AccessUser String userId,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String content
    );

    @Operation(summary = "일기 탐색")
    @GetMapping("/explore")
    @Parameters({
            @Parameter(
                    name = "page",
                    description = "요청할 페이지 번호"
            ),
            @Parameter(
                    name = "size",
                    description = "요청할 페이지 크기"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 탐색 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "쿼리 스트링 오류"
            )
    })
    SliceResponse<DiaryThumbnail> exploreDiary(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam ExploreOrder order
    );

    @Operation(summary = "좋아요 누른 일기 조회")
    @GetMapping("/like")
    @Parameters({
            @Parameter(
                    name = "page",
                    description = "요청할 페이지 번호"
            ),
            @Parameter(
                    name = "size",
                    description = "요청할 페이지 크기"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 탐색 성공"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "쿼리 스트링 오류"
            )
    })
    SliceResponse<DiaryThumbnail> readLikedDiary(
            @AccessUser String userId,
            @RequestParam Integer page,
            @RequestParam Integer size
    );
}
