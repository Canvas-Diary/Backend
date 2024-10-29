package com.canvas.persistence.jpa.user.repository;

import com.canvas.persistence.jpa.user.entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserTokenJpaRepository extends JpaRepository<UserTokenEntity, UUID> {


    boolean existsByToken(String token);

    void deleteByTokenAndUserId(String token, UUID userId);


}
