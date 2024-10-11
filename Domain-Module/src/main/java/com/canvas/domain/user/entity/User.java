package com.canvas.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {
    private final Long id;
    private final String email;
    private final String username;

    public User withoutId(String email, String username) {
        return new User(null, email, username);
    }

    public User withId(Long id, String email, String username) {
        return new User(id, email, username);
    }
}
