package com.canvas.application.user.port.out;

import com.canvas.domain.user.entity.User;

public interface DeleteUserPort {
    void delete(User user);
}
