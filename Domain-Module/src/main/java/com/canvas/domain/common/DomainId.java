package com.canvas.domain.common;

import com.github.f4b6a3.ulid.UlidCreator;

import java.util.UUID;

public record DomainId(
        UUID value
) {
    public static DomainId generate() {
        return new DomainId(UlidCreator.getMonotonicUlid().toUuid());
    }
}
