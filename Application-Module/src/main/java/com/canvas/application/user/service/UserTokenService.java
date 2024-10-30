package com.canvas.application.user.service;

import com.canvas.application.user.port.in.LoginUserUseCase;
import com.canvas.application.user.port.in.LogoutUserCase;
import com.canvas.application.user.port.in.ReissueTokenUseCase;
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
public class UserTokenService implements LoginUserUseCase, ReissueTokenUseCase, LogoutUserCase {

    private final UserTokenManagementPort userTokenManagementPort;
    private final UserTokenConvertPort userTokenConvertPort;
    private final UserManagementPort userManagementPort;
    private final AuthInfoRetrievePort authInfoRetrievePort;

    @Override
    public LoginUserUseCase.Response login(LoginUserUseCase.Command command) {
        String oauthAccessToken = authInfoRetrievePort.getAccessToken(command.provider(), command.code());
        OauthUserInfo userInfo = authInfoRetrievePort.getUserInfo(command.provider(), oauthAccessToken);


        //회원가입 여부 확인
        User user;
        if(userManagementPort.existsBySocialIdAndProviderId(userInfo.socialId(), SocialLoginProvider.valueOf(command.provider()))) {
            user = userManagementPort.getById(DomainId.generate());
        } else {
            //존재하면 유저정보 가져오기
            user = userManagementPort.getBySocialIdAndProvider(userInfo.socialId(), SocialLoginProvider.valueOf(command.provider()));
        }

        String userId = user.getDomainId().toString();

        // 가져온 유저정보 넣기
        String accessToken = userTokenConvertPort.createAccessToken(userId);
        String refreshToken = userTokenConvertPort.createRefreshToken(userId);

        // refreshToken 저장
        userTokenManagementPort.save(new UserToken(DomainId.generate(), refreshToken, DomainId.from(userId)));

        return new LoginUserUseCase.Response(accessToken, refreshToken);
    }

    @Override
    public ReissueTokenUseCase.Response reissue(ReissueTokenUseCase.Command command) {
        UserClaim userClaim = userTokenConvertPort.resolveRefreshToken(command.refreshToken());

        userTokenManagementPort.deleteByTokenAndUserId(command.refreshToken(), DomainId.from(userClaim.userId()));

        String accessToken = userTokenConvertPort.createAccessToken(userClaim.userId());
        String refreshToken = userTokenConvertPort.createRefreshToken(userClaim.userId());

        userTokenManagementPort.save(new UserToken(DomainId.generate(), refreshToken, DomainId.from(userClaim.userId())));


        return new ReissueTokenUseCase.Response(accessToken, refreshToken);

    }

    @Override
    public void logout(LogoutUserCase.Command command) {
        userTokenManagementPort.deleteByTokenAndUserId(command.refreshToken(), DomainId.from(command.userId()));
    }
}
