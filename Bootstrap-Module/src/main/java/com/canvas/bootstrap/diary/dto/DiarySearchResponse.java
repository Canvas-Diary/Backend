package com.canvas.bootstrap.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "앨범에서 일기 검색에 대한 응답")
public record DiarySearchResponse(

    @Schema(description = "일기 썸내일 정보 리스트 반환")
    List<DiaryThumbnail> diaries

) { }
