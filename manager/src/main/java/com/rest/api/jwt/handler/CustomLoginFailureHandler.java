package com.rest.api.jwt.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMsg = "";

        if(exception instanceof BadCredentialsException) {
            //비밀번호 입력 오류, ID 입력 오류
        }

        StringBuilder sb = new StringBuilder();
        sb.append("/login?error");

        if(!errorMsg.equals("")) {
            sb.append("=").append(errorMsg);
        }

        response.sendRedirect(sb.toString());
    }
}
