package com.canvas.bootstrap.auth.api;

import com.canvas.bootstrap.auth.dto.LogoutRequest;
import com.canvas.bootstrap.auth.dto.ReissueRequest;
import com.canvas.bootstrap.auth.dto.ReissueResponse;
import com.canvas.bootstrap.common.annotation.AccessUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Auth API")
@RequestMapping("/api/v1/auth")
public interface AuthApi {

    @Operation(summary = "회원가입 or 로그인")
    @GetMapping("/{provider}/callback")
    @ApiResponses()
    void login(@PathVariable String provider, @RequestParam String code, HttpServletResponse httpServletResponse);


    @Operation
    @PostMapping("/reissue")
    @ApiResponses()
    ReissueResponse reissue(@RequestBody ReissueRequest reissueRequest);

    @Operation
    @PostMapping("/logout")
    @ApiResponses()
    void logout(@AccessUser String userId, LogoutRequest logoutRequest);
}
