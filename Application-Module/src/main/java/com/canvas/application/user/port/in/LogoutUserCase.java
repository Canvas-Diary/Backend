package com.canvas.application.user.port.in;

public interface LogoutUserCase {
    void logout(Command command);

    record Command(
            String userId,
            String refreshToken
    ) {

    }


}
