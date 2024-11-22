package com.canvas.bootstrap.common.filter;

import com.canvas.application.user.port.in.TokenResolveUserCase;
import com.canvas.application.user.vo.UserClaim;
import com.canvas.bootstrap.common.filter.exception.AuthenticationException;
import com.canvas.common.security.UserAuthentication;
import com.canvas.common.security.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Order(2)
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Set<String> excludedAuthUrls = new HashSet<>();

    static {
        excludedAuthUrls.add("/api/v1/auth/**");
        excludedAuthUrls.add("/swagger-ui/**");
        excludedAuthUrls.add("/v3/api-docs/**");
        excludedAuthUrls.add("/actuator/**");
    }

    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final TokenResolveUserCase tokenResolveUserCase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 제외 되는 경로 검증
        String requestURI = request.getRequestURI();

        if (isExcludedPath(requestURI) || request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            throw new AuthenticationException();
        }

        String accessToken = authorization.substring(BEARER_PREFIX.length());
        // 토큰 검증
        UserClaim userClaim = tokenResolveUserCase.resolveToken(accessToken);
        // 에노테이션에 담기
        UserContextHolder.setContext(new UserAuthentication(userClaim.userId()));

        filterChain.doFilter(request, response);

        // 에노테이션에 값 제거
        UserContextHolder.clear();
    }

    private boolean isExcludedPath(String requestURI) {
        return excludedAuthUrls.stream()
                               .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }

}
