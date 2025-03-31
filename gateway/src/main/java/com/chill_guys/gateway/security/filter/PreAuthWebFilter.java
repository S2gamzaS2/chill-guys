package com.chill_guys.gateway.security.filter;

import com.chill_guys.gateway.jwt.JwtUtil;
import com.chill_guys.gateway.security.service.BlackListCheck;
import io.jsonwebtoken.Claims;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j(topic = "PreAuthWebFilter")
public class PreAuthWebFilter implements WebFilter, Ordered {

    private final JwtUtil jwtUtil;
    private final BlackListCheck blackListCheck;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("PreAuthWebFilter 필터 실행");

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("추출한 Authorization 헤더: {}", authHeader);

        if (authHeader == null) {
            return chain.filter(exchange);
        }

        String accessToken = jwtUtil.removeBearer(authHeader);
        log.info("Bearer 제거 후 토큰: {}", accessToken);

        // 쿠키에서 refreshToken 가져오기
        var cookies = exchange.getRequest().getCookies().get("refreshToken");
        String refreshToken = cookies.get(0).getValue();
        log.info("추출한 refreshToken 쿠키: {}", refreshToken);

        // accessToken 검증
        if (!jwtUtil.validateToken(accessToken)) {
            log.error("유효하지 않은 토큰입니다.");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // refreshToken 검증
        if (!jwtUtil.validateToken(refreshToken)) {
            log.error("유효하지 않은 토큰입니다.");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // accessToken 블랙리스트 체크
        if (blackListCheck.isBlacklisted(accessToken, "AT")) {
            log.error("블랙리스트 토큰입니다. accessToken : " + accessToken);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // refreshToken 블랙리스트 체크
        if (blackListCheck.isBlacklisted(accessToken, "RT")) {
            log.error("블랙리스트 토큰입니다. refreshToken : " + accessToken);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        Claims claims = jwtUtil.getClaims(accessToken);
        String userId = claims.getSubject();
        String username = claims.get("username", String.class);
        String role = claims.get("role", String.class);
        log.info("토큰에서 추출한 클레임 - userId: {}, username: {}, role: {}", userId, username, role);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            username,
            null,
            Collections.singletonList(new SimpleGrantedAuthority(role))
        );
        authToken.setDetails(userId);

        SecurityContext securityContext = new SecurityContextImpl(authToken);

        ServerWebExchange mutatedExchange = exchange.mutate()
            .request(r -> r.headers(headers -> {
                headers.set("X-User-Id", userId);
                headers.set("X-User-Name", username);
                headers.set("X-User-Role", role);
                headers.set("X-ACCESS-TOKEN", accessToken);
                headers.set("X-REFRESH-TOKEN", refreshToken);
            }))
            .build();

        return chain.filter(mutatedExchange)
            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

}
