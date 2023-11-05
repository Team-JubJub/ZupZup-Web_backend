package com.zupzup.untact.config.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ManagerJwtAuthenticationFilter extends OncePerRequestFilter {

    private final ManagerJwtProvider managerJwtProvider;

    public ManagerJwtAuthenticationFilter(ManagerJwtProvider managerJwtProvider) {
        this.managerJwtProvider = managerJwtProvider;
    }

    /**
     * Jwt 가 유효성을 검증하는 Filter
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = managerJwtProvider.resolveToken(request);

        if (token != null && managerJwtProvider.validateToken(token)) {
            // check access token
            token = token.split(" ")[1].trim();
            Authentication auth = managerJwtProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
