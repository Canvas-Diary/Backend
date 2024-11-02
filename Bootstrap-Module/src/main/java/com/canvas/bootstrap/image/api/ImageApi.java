package com.canvas.bootstrap.image.api;

import com.canvas.bootstrap.common.annotation.AccessUser;
import com.canvas.bootstrap.image.dto.CreateImageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Image", description = "Image API")
@RequestMapping("/api/v1/diaries/{diaryId}/images")
public interface ImageApi {

    @Operation(summary = "이미지 생성")
    @PostMapping()
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "이미지 생성 성공"
            )
    })
    void createImage(@AccessUser String userId, @PathVariable String diaryId, @RequestBody CreateImageRequest createImageRequest);

    @Operation(summary = "이미지 삭제")
    @DeleteMapping("/{imageId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "이미지 삭제 성공"
            )
    })
    void deleteImage(@AccessUser String userId,  @PathVariable String diaryId, @PathVariable String imageId);

    @Operation(summary = "일기 대표 이미지 수정")
    @PatchMapping("/{imageId}")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "일기 대표 이미지 수정 성공"
            )
    })
    void updateMainImage(@AccessUser String userId, @PathVariable String diaryId, @PathVariable String imageId);

}
