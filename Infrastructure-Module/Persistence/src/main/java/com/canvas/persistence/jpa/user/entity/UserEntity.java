package com.canvas.persistence.jpa.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String username;
    private String socialId;
    private String socialLoginProvider;

}
