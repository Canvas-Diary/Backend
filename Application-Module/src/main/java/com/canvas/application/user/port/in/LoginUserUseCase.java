package com.canvas.application.user.port.in;

public interface LoginUserUseCase {
    Response login(Command command);

    record Command(
            String provider,
            String code
    ) {
    }


    record Response(
                String accessToken,
                String refreshToken
        ) {
        }
}
