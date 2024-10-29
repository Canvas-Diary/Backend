package com.canvas.persistence.jpa.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserTokenEntity {

    @Id
    private UUID id;

    private String token;
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity userEntity;

    public UserTokenEntity(UUID id, String token, UUID userId) {
        this.id = id;
        this.token = token;
        this.userId = userId;
    }
}
