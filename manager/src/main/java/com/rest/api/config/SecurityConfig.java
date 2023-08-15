package com.rest.api.config;

import com.rest.api.jwt.JwtAuthenticationFilter;
import com.rest.api.jwt.JwtTokenProvider;
import com.rest.api.jwt.handler.CustomLoginFailureHandler;
import com.rest.api.jwt.handler.CustomLoginSuccessHandler;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.MatcherType.mvc;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig webConfig;

    //비밀번호 암호화
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .authorizeHttpRequests(auth -> {
//                    auth.anyRequest().authenticated();
//                    auth.requestMatchers(antMatcher("/**")).permitAll();
//                    auth.anyRequest().authenticated();
//                })
                //.anyRequest().hasRole("USER") //다른 url로 접근 시 USER 권한이 있어야 함
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // 로그아웃 처리 URL
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(((request, response, authentication) -> response.sendRedirect("/")))
                .addLogoutHandler((request, response, authentication) -> {
                    //System.out.println("logout Success!");
                    HttpSession session = request.getSession();
                    session.invalidate();
                })
                // cors 설정
                .and().cors().configurationSource(webConfig.corsConfigurationSource());

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)  throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        SimpleUrlAuthenticationSuccessHandler successHandler = new CustomLoginSuccessHandler();
        successHandler.setDefaultTargetUrl("/");

        return successHandler;
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new CustomLoginFailureHandler();
    }
}