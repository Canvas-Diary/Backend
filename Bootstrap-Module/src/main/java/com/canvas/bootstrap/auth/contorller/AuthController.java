package com.canvas.bootstrap.auth.contorller;

import com.canvas.application.user.port.in.LoginUserUseCase;
import com.canvas.application.user.port.in.ReissueTokenUseCase;
import com.canvas.bootstrap.auth.api.AuthApi;
import com.canvas.bootstrap.auth.dto.LoginResponse;
import com.canvas.bootstrap.auth.dto.ReissueRequest;
import com.canvas.bootstrap.auth.dto.ReissueResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final LoginUserUseCase loginUserUseCase;
    private final ReissueTokenUseCase reissueTokenUseCase;

    @Override
    public LoginResponse login(String provider, String code) {

        LoginUserUseCase.Command command = new LoginUserUseCase.Command(provider, code);

        LoginUserUseCase.Response response = loginUserUseCase.login(command);

        return new LoginResponse(response.accessToken(), response.refreshToken());

    }

    @Override
    public ReissueResponse reissue(ReissueRequest reissueRequest) {
        ReissueTokenUseCase.Command command = new ReissueTokenUseCase.Command(reissueRequest.refreshToken());

        ReissueTokenUseCase.Response response = reissueTokenUseCase.reissue(command);

        return new ReissueResponse(response.accessToken());
    }
}
