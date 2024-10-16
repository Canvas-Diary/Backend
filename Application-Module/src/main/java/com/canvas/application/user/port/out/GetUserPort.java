package com.canvas.application.user.port.out;

import com.canvas.domain.user.entity.User;

public interface GetUserPort {
    User get(Long userId);
}
