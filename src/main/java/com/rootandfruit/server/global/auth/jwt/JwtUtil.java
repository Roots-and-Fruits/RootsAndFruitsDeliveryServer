package com.rootandfruit.server.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    // JWT 생성
    public String generateToken(String userName, String role) {
        return Jwts.builder()
                .setSubject(userName)
                .claim("role", role) // 역할 정보 추가
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    // 토큰에서 역할 확인
    public boolean isAdmin(String token) throws Exception {
        Claims claims = getBody(token);
        String role = claims.get("role", String.class);
        return "ROLE_ADMIN".equals(role);
    }

    private SecretKey getSigningKey() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes()); // SecretKey를 통해 서명 생성

        // 일반적으로 HMAC (Hash-based Message Authentication Code) 알고리즘을 사용
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }


    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
