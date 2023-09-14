package com.zupzup.untact.config.auth;

import com.zupzup.untact.model.auth.ManagerAuthority;
import com.zupzup.untact.service.auth.JpaUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;
    private Key secretKey;
    // 만료시간 1시간
    private final long exp = 1000L * 60 * 60;
    private final JpaUserDetailService userDetailService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(String loginId, List<ManagerAuthority> roles) {

        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("role", roles);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한 확인을 위한 기능
    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailService.loadUserByUsername(this.getLoginId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저 loginId 획득
    public String getLoginId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    // Authorization Header 를 통해 인증함
    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {

        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
