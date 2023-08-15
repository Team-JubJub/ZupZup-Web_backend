package com.rest.api.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 토큰 받아오기
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        // 유효한 토큰인지 확인 -> validation 진행
        if (token != null && jwtTokenProvider.validateToken(token)) {

            //토큰으로부터 유저 정보를 받아
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            //SecurityContext 에 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //다음 Filter 실행
        chain.doFilter(request, response);
    }
}