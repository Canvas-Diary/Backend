package com.canvas.bootstrap.common.filter;

import com.canvas.application.user.port.in.TokenResolveUserCase;
import com.canvas.application.user.vo.UserClaim;
import com.canvas.common.security.UserAuthentication;
import com.canvas.common.security.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final List<String> excludedAuthUrls;
    private final PathMatcher pathMatcher = new AntPathMatcher();
    private final TokenResolveUserCase tokenResolveUserCase;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 제외 되는 경로 검증
        String requestURI = request.getRequestURI();

        if (isExcludedPath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if(authorization != null && authorization.startsWith(BEARER_PREFIX)) {
            String accessToken = authorization.substring(BEARER_PREFIX.length());
            // 토큰 검증
            UserClaim userClaim = tokenResolveUserCase.resolveToken(accessToken);
            // 에노테이션에 담기
            UserContextHolder.setContext(new UserAuthentication(userClaim.userId()));
        }

        filterChain.doFilter(request, response);

        // 에노테이션에 값 제거
        UserContextHolder.clear();
    }

    private boolean isExcludedPath(String requestURI) {
        return excludedAuthUrls.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }

}