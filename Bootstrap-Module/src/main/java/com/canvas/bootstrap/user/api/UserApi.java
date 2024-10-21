package com.canvas.bootstrap.user.api;

import com.canvas.bootstrap.user.dto.RegisterUserRequest;
import com.canvas.bootstrap.user.dto.RegisterUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "User API")
public interface UserApi {
    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 성공",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RegisterUserResponse.class)
                            )
                    })
    })
    RegisterUserResponse register(RegisterUserRequest request);
}
