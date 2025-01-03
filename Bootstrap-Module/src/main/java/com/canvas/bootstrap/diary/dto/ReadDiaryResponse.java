package com.canvas.bootstrap.diary.dto;

import com.canvas.domain.diary.enums.Emotion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "일기 조회 반환")
public record ReadDiaryResponse(
        @Schema(description = "일기 ID")
        String diaryId,
        @Schema(description = "일기 내용")
        String content,
        @Schema(description = "강조된 일기 내용")
        List<String> weightedContents,
        @Schema(description = "일기의 감정")
        Emotion emotion,
        @Schema(description = "일기 좋아요 수")
        Integer likedCount,
        @Schema(description = "일기 좋아요 선택 여부")
        Boolean isLiked,
        @Schema(description = "내 일기인지 여부")
        Boolean isMine,
        @Schema(description = "일기 생성 날짜")
        LocalDate date,
        @Schema(description = "일기 공개 여부")
        Boolean isPublic,
        @Schema(description = "일기의 이미지 정보 리스트")
        List<ImageInfo> images) {
    @Schema(description = "일기의 이미지 정보")
    public record ImageInfo(
            @Schema(description = "이미지 ID")
            String imageId,
            @Schema(description = "대표 이미지 여부")
            Boolean isMain,
            @Schema(description = "저장된 일기 이미지 url")
            String imageUrl
    ) {
    }
}
