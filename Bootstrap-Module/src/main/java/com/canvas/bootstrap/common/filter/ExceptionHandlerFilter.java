package com.canvas.bootstrap.common.filter;

import com.canvas.bootstrap.common.exception.ExceptionResponse;
import com.canvas.common.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(0)
@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handleException(response, e);
        }
    }

    private void handleException(HttpServletResponse response, Exception e) throws IOException {
        int status = getStatus(e);
        setResponseHeaders(response, status);

        ExceptionResponse errorResponse = new ExceptionResponse("000", e.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private int getStatus(Exception e) {
        if (e instanceof BusinessException) {
            log.error(e.getMessage());
            return HttpServletResponse.SC_UNAUTHORIZED;
        }
        log.error("알 수 없는 서버 오류: {0}", e);

        return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    private void setResponseHeaders(HttpServletResponse response, int status) {
        response.setStatus(status);
        response.addHeader("Access-Control-Allow-Origin", "http://www.canvas-diary.kro.kr");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

}
