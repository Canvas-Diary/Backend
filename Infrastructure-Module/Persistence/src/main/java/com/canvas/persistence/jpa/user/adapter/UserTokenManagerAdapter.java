package com.canvas.persistence.jpa.user.adapter;

import com.canvas.application.user.port.out.UserTokenManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.UserToken;
import com.canvas.persistence.jpa.user.UserMapper;
import com.canvas.persistence.jpa.user.entity.UserTokenEntity;
import com.canvas.persistence.jpa.user.repository.UserTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserTokenManagerAdapter implements UserTokenManagementPort {

    private final UserTokenJpaRepository userTokenJpaRepository;


    @Override
    public UserToken save(UserToken userToken) {
        UserTokenEntity userTokenEntity = userTokenJpaRepository.save(UserMapper.toEntity(userToken));
        return UserMapper.toDomain(userTokenEntity);
    }

    @Override
    public void deleteByTokenAndUserId(String token, DomainId userId) {
        userTokenJpaRepository.deleteByTokenAndUserId(token, userId.value());
    }


    @Override
    public boolean existsByToken(String token) {
        return userTokenJpaRepository.existsByToken(token);
    }
}
