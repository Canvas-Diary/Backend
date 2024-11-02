package com.canvas.bootstrap.auth.contorller;

import com.canvas.application.user.port.in.LoginUserUseCase;
import com.canvas.application.user.port.in.LogoutUserCase;
import com.canvas.application.user.port.in.ReissueTokenUseCase;
import com.canvas.bootstrap.auth.api.AuthApi;
import com.canvas.bootstrap.auth.dto.LogoutRequest;
import com.canvas.bootstrap.auth.dto.ReissueRequest;
import com.canvas.bootstrap.auth.dto.ReissueResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final LoginUserUseCase loginUserUseCase;
    private final ReissueTokenUseCase reissueTokenUseCase;
    private final LogoutUserCase logoutUserCase;

    @Override
    public void login(String provider, String code, HttpServletResponse httpServletResponse) {

        LoginUserUseCase.Command command = new LoginUserUseCase.Command(provider, code);

        LoginUserUseCase.Response response = loginUserUseCase.login(command);

        String uri = UriComponentsBuilder.fromUriString("http://www.canvas-diary.kro.kr")
                                         .path("/")
                                         .queryParam("access", response.accessToken())
                                         .queryParam("refresh", response.refreshToken())
                                         .build()
                                         .toUriString();

        try {
            httpServletResponse.sendRedirect(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReissueResponse reissue(ReissueRequest reissueRequest) {
        ReissueTokenUseCase.Command command = new ReissueTokenUseCase.Command(reissueRequest.refreshToken());

        ReissueTokenUseCase.Response response = reissueTokenUseCase.reissue(command);

        return new ReissueResponse(response.accessToken());
    }

    @Override
    public void logout(String userId, LogoutRequest logoutRequest) {
        logoutUserCase.logout(new LogoutUserCase.Command(
                userId, logoutRequest.refreshToken()
        ));
    }
}
