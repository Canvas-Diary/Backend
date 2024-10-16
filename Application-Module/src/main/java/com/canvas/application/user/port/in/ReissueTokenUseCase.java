package com.canvas.application.user.port.in;

public interface ReissueTokenUseCase {
    Response reissue(Command command);

    record Command(
            String refreshToken
    ) {
    }

    record Response(
            String accessToken,
            String refreshToken
    ) {
    }
}
