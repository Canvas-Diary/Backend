package com.canvas.persistence.jpa.user.repository;

import com.canvas.persistence.jpa.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
}
