package com.canvas.common.security;

public class UserContextHolder {

    private static final ThreadLocal<UserAuthentication> userClaimHolder = new ThreadLocal<>();

    public static void setContext(UserAuthentication userAuthentication) {
        userClaimHolder.set(userAuthentication);
    }

    public static UserAuthentication getContext() {
        return userClaimHolder.get();
    }

    public static void clear() {
        userClaimHolder.remove();
    }
}
