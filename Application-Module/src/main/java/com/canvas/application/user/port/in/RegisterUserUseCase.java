package com.canvas.application.user.port.in;

public interface RegisterUserUseCase {
    Response register(Command command);

    record Command(
            String registerToken,
            String username
    ) {
    }

    record Response(
            String accessToken,
            String refreshToken
    ) {
    }
}
