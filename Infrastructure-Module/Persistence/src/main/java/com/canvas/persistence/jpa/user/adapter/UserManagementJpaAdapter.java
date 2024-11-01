package com.canvas.persistence.jpa.user.adapter;

import com.canvas.application.user.port.out.UserManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.User;
import com.canvas.domain.user.enums.SocialLoginProvider;
import com.canvas.persistence.jpa.user.UserMapper;
import com.canvas.persistence.jpa.user.entity.UserEntity;
import com.canvas.persistence.jpa.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserManagementJpaAdapter implements UserManagementPort {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = userJpaRepository.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public User getById(DomainId userId) {
        UserEntity userEntity = userJpaRepository.findById(userId.value())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자"));
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public void delete(User user) {
        userJpaRepository.deleteById(user.getDomainId().value());
    }

    @Override
    public boolean existsBySocialIdAndProviderId(String socialId, SocialLoginProvider provider) {
        return userJpaRepository.existsBySocialIdAndSocialLoginProvider(socialId, provider.name());
    }


    @Override
    public User getBySocialIdAndProvider(String socialId, SocialLoginProvider provider) {
        UserEntity userEntity = userJpaRepository.findBySocialIdAndSocialLoginProvider(socialId, provider.name());
        return UserMapper.toDomain(userEntity);
    }

}
