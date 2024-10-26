package com.canvas.bootstrap.like.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/{diaryId}/like")
public interface LikeApi {

    @Operation(summary = "일기 좋아요 추가")
    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "좋아요 추가 성공"
            )
    })
    void addLike(@PathVariable String diaryId);

    @Operation(summary = "일기 좋아요 삭제")
    @DeleteMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "좋아요 삭제 성공"
            )
    })
    void deleteLike(@PathVariable String diaryId);
}
