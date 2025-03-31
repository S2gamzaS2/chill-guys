package com.chill_guys.user_service.auth.presentation;

import com.chill_guys.user_service.auth.application.dto.request.AuthLoginRequestDto;
import com.chill_guys.user_service.auth.application.dto.request.AuthSignupRequestDto;
import com.chill_guys.user_service.auth.application.dto.response.AuthSignupResponseDto;
import com.chill_guys.user_service.auth.application.dto.response.AuthTokenRefreshResponseDto;
import com.chill_guys.user_service.auth.application.dto.response.AuthTokenResponseDto;
import com.chill_guys.user_service.auth.application.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    // 회원가입
    @PostMapping("/auth/signup")
    public ResponseEntity<AuthSignupResponseDto> createUser(@Valid @RequestBody AuthSignupRequestDto requestDto,
                                                            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader
    ) {
        AuthSignupResponseDto responseDto = authService.createUser(requestDto, userIdHeader);
        return ResponseEntity.ok(responseDto);
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<AuthTokenResponseDto> loginUser(@RequestBody AuthLoginRequestDto requestDto,
                                                          HttpServletResponse servletResponse
    ) {
        AuthTokenResponseDto responseDto = authService.loginUser(requestDto);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", responseDto.getRefreshToken())
            .httpOnly(true)
            .secure(true)
            .maxAge(Duration.ofDays(1))
            .path("/")
            .build();
        servletResponse.addHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.ok(responseDto);
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public ResponseEntity<String> logoutUser(@RequestHeader(value = "X-User-Id") String userIdHeader,
                                             @RequestHeader(value = "X-ACCESS-TOKEN") String accessToken,
                                             @RequestHeader(value = "X-REFRESH-TOKEN") String refreshToken
        ) {
        authService.logoutUser(accessToken, refreshToken, userIdHeader);
        return ResponseEntity.ok("로그아웃이 완료되었습니다.");
    }

    // Token 재발급
    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthTokenRefreshResponseDto> tokenRefresh(@RequestHeader(value = "X-User-Id") String userIdHeader,
                                                                    @RequestHeader(value = "X-REFRESH-TOKEN") String oldRefreshToken,
                                                                    HttpServletResponse servletResponse
        ) {
        AuthTokenRefreshResponseDto responseDto = authService.tokenRefresh(oldRefreshToken, userIdHeader);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", responseDto.getNewRefreshToken())
            .httpOnly(true)
            .secure(true)
            .maxAge(Duration.ofDays(1))
            .path("/")
            .build();
        servletResponse.addHeader("Set-Cookie", refreshTokenCookie.toString());

        return ResponseEntity.ok(responseDto);
    }

}
