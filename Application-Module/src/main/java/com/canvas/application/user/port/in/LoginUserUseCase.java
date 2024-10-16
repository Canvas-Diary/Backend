package com.canvas.application.user.port.in;

public interface LoginUserUseCase {
    Response login(Command command);

    record Command(
            String provider,
            String socialLoginProviderToken
    ) {
    }

    sealed interface Response
            permits Response.Login, Response.Register {
        public record Login(
                String accessToken,
                String refreshToken
        ) implements Response {
        }

        public record Register(
                String registerToken
        ) implements Response {
        }
    }
}
