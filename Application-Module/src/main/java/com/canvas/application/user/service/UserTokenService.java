package com.canvas.application.user.service;

import com.canvas.application.user.port.in.LoginUserUseCase;
import com.canvas.application.user.port.in.LogoutUserCase;
import com.canvas.application.user.port.in.ReissueTokenUseCase;
import com.canvas.application.user.port.in.TokenResolveUserCase;
import com.canvas.application.user.port.out.AuthInfoRetrievePort;
import com.canvas.application.user.port.out.UserManagementPort;
import com.canvas.application.user.port.out.UserTokenConvertPort;
import com.canvas.application.user.port.out.UserTokenManagementPort;
import com.canvas.application.user.vo.OauthUserInfo;
import com.canvas.application.user.vo.UserClaim;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.user.entity.User;
import com.canvas.domain.user.entity.UserToken;
import com.canvas.domain.user.enums.SocialLoginProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserTokenService implements LoginUserUseCase, ReissueTokenUseCase, LogoutUserCase, TokenResolveUserCase {

    private final UserTokenManagementPort userTokenManagementPort;
    private final UserTokenConvertPort userTokenConvertPort;
    private final UserManagementPort userManagementPort;
    private final AuthInfoRetrievePort authInfoRetrievePort;

    @Override
    public LoginUserUseCase.Response login(LoginUserUseCase.Command command) {
        String oauthAccessToken = authInfoRetrievePort.getAccessToken(command.provider(), command.code());
        OauthUserInfo userInfo = authInfoRetrievePort.getUserInfo(command.provider(), oauthAccessToken);

        User user = getUserOrRegister(userInfo, command.provider());

        String userId = user.getId().toString();

        String accessToken = userTokenConvertPort.createAccessToken(userId);
        String refreshToken = userTokenConvertPort.createRefreshToken(userId);

//        userTokenManagementPort.save(UserToken.create(DomainId.generate(), refreshToken, DomainId.from(userId)));

        return new LoginUserUseCase.Response(accessToken, refreshToken);
    }

    @Override
    public ReissueTokenUseCase.Response reissue(ReissueTokenUseCase.Command command) {
        UserClaim userClaim = userTokenConvertPort.resolveRefreshToken(command.refreshToken());

        String accessToken = userTokenConvertPort.createAccessToken(userClaim.userId());

        return new ReissueTokenUseCase.Response(accessToken);

    }

    @Override
    public void logout(LogoutUserCase.Command command) {
        userTokenManagementPort.deleteByTokenAndUserId(command.refreshToken(), DomainId.from(command.userId()));
    }


    private User getUserOrRegister(OauthUserInfo userInfo, String provider) {
        if(userManagementPort.existsBySocialIdAndProviderId(userInfo.socialId(), SocialLoginProvider.parse(provider))) {
            return userManagementPort.getBySocialIdAndProvider(userInfo.socialId(), SocialLoginProvider.parse(provider));
        } else {
            return userManagementPort.save(User.create(DomainId.generate(), userInfo.username(),
                    userInfo.socialId(), SocialLoginProvider.parse(provider)));
        }
    }

    @Override
    public UserClaim resolveToken(String accessToken) {
        return userTokenConvertPort.resolveAccessToken(accessToken);
    }
}
