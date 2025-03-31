package com.chill_guys.user_service.auth.jwt;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "JwtUil")
public class JwtUtil {
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    @Value("${jwt.access.exp}")
    private Long jwtAccessExpTime;

    @Value("${jwt.refresh.exp}")
    private Long jwtRefreshExpTime;

    private Key key;

    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    // Access 토큰 생성
    public String createAccessToken(Long userId,String username, String role) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtAccessExpTime);

        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("username", username)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    // refresh 토큰 생성
    public String createRefreshToken(Long userId) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtRefreshExpTime);

        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .setIssuedAt(now)
            .setExpiration(expirationDate)
            .signWith(key, signatureAlgorithm)
            .compact();
    }

    // 토큰 만료 시간 반환
    public Date getExpiration(String token) {
        Claims claims = getUserInfoFromToken(token);
        return claims.getExpiration();
    }

    // 토큰 만료시간 계산
    public Long getMilliSecond(String token) {
        Date exp = getExpiration(token);
        long now = System.currentTimeMillis();
        return exp.getTime() - now;
    }

    // 토큰에서 user 데이터 가져오기
    public Claims getUserInfoFromToken(String token) {
        // 토큰 파싱하여 리턴
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

}
