package com.canvas.domain.common;

import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;

import java.util.Objects;
import java.util.UUID;

public record DomainId(
        UUID value
) {
    public static DomainId generate() {
        return new DomainId(UlidCreator.getMonotonicUlid().toUuid());
    }

    public static DomainId from(String id) {
        return new DomainId(Ulid.from(id).toUuid());
    }

    public static DomainId from(UUID uuid) {
        return new DomainId(uuid);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof DomainId domainId)) return false;
        return Objects.equals(value, domainId.value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
