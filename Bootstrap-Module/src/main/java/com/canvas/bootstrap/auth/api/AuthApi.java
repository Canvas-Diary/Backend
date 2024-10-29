package com.canvas.bootstrap.auth.api;

import com.canvas.bootstrap.auth.dto.LoginResponse;
import com.canvas.bootstrap.auth.dto.ReissueRequest;
import com.canvas.bootstrap.auth.dto.ReissueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Auth API")
@RequestMapping("api/v1/auth/{provider}")
public interface AuthApi {

    @Operation(summary = "회원가입 or 로그인")
    @GetMapping("/callback")
    @ApiResponses()
    LoginResponse login(@PathVariable String provider, @RequestParam String code);


    @Operation
    @PostMapping("/reissue")
    @ApiResponses
    ReissueResponse reissue(@RequestBody ReissueRequest reissueRequest);
}
