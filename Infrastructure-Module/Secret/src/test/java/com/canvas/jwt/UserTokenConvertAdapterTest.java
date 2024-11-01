package com.canvas.jwt;

import com.canvas.application.user.vo.UserClaim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

//TODO mock 데이터로 바꿔야 함
class UserTokenConvertAdapterTest {

    private JwtProperties jwtProperties;

    private UserTokenConvertAdapter userTokenConvertAdapter;

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        jwtProperties = new JwtProperties();
        Field secretField = JwtProperties.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtProperties, "WS4UeAaZRGA181MfwNJZRgMJbVdPKSx1LZ1futg2Zs0=");

        userTokenConvertAdapter = new UserTokenConvertAdapter(jwtProperties);
    }


    @Test
    void getToken() {
        String token = userTokenConvertAdapter.createAccessToken("1234");
        UserClaim userClaim = userTokenConvertAdapter.resolveAccessToken(token);
        System.out.println(userClaim);
    }
}