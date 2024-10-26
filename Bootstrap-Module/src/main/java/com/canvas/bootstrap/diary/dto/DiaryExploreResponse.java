package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "상대방 일기 탐색 응답")
public record DiaryExploreResponse(

    @Schema(description = "일기 썸내일 정보 리스트 반환")
    List<DiaryThumbnail> diaries

) {}
