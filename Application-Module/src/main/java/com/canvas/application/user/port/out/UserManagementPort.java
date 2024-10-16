package com.canvas.application.user.port.out;

import com.canvas.domain.user.entity.User;

public interface UserManagementPort {
    User save(User user);
    User get(Long userId);
    void delete(User user);
}
