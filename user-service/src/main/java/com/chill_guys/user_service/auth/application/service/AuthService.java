package com.chill_guys.user_service.auth.application.service;

import com.chill_guys.user_service.auth.application.dto.request.AuthLoginRequestDto;
import com.chill_guys.user_service.auth.application.dto.request.AuthSignupRequestDto;
import com.chill_guys.user_service.auth.application.dto.response.AuthSignupResponseDto;
import com.chill_guys.user_service.auth.application.dto.response.AuthTokenRefreshResponseDto;
import com.chill_guys.user_service.auth.application.dto.response.AuthTokenResponseDto;
import com.chill_guys.user_service.user.domain.entity.User;
import com.chill_guys.user_service.user.domain.repository.UserRepository;
import com.chill_guys.user_service.auth.jwt.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.refresh.exp}")
    private Long jwtRefreshExpTime;

    // 회원가입
    public AuthSignupResponseDto createUser(AuthSignupRequestDto requestDto, String userIdHeader) {
        Long userId = parseOrDefault(userIdHeader);
        User user = User.fromRequest(requestDto);
        user.createInfo(userId);
        userRepository.save(user);

        return AuthSignupResponseDto.builder()
            .username(user.getUsername())
            .slackId(user.getSlackId())
            .build();
    }

    // 로그인
    public AuthTokenResponseDto loginUser(AuthLoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() ->
            new EntityNotFoundException("해당하는 사용자가 존재하지 않습니다. 받은 username : " + requestDto.getUsername()));

        boolean passwordMatch = BCrypt.checkpw(requestDto.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getUsername(), user.getRole().name());
        String refreshToken = jwtUtil.createRefreshToken(user.getId());

        // 레디스에 리프레쉬 토큰 저장
        stringRedisTemplate.opsForValue().set(
            "whitelist:RT:" + user.getId(),
            refreshToken,
            jwtRefreshExpTime,
            TimeUnit.MILLISECONDS
        );

        return AuthTokenResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }

    // 로그아웃
    public void logoutUser(String accessToken, String refreshToken, String userIdHeader) {
        /*
         * 이 부분만 Wrapper 타입인 Long 으로 선언하지 않은 이유는
         * userRepository 에서 찾아오는 로직이 없기 때문에 null 값을 허용할 이유가 없음.
         * parseLong()이 반환하는 원시타입인 long 으로 받도록 설정.
         */
        long userId = Long.parseLong(userIdHeader);

        Long accessTokenExpCheck = jwtUtil.getMilliSecond(accessToken);
        Long refreshTokenExpCheck = jwtUtil.getMilliSecond(refreshToken);

        // 레디스에 블랙리스트 엑세스 토큰 저장.
        stringRedisTemplate.opsForValue().set(
            "blacklist:AT:" + accessToken,
            "LOGOUT",
            accessTokenExpCheck,
            TimeUnit.MILLISECONDS
        );

        // 레디스에 블랙리스트 리프레쉬 토큰 저장.
        stringRedisTemplate.opsForValue().set(
            "blacklist:RT:" + refreshToken,
            "LOGOUT",
            refreshTokenExpCheck,
            TimeUnit.MILLISECONDS
        );

        stringRedisTemplate.delete("whitelist:RT:" + userId);

    }

    // 엑세스 토큰 만료시 리프레쉬 토큰 발급
    public AuthTokenRefreshResponseDto tokenRefresh(String oldRefreshToken, String userIdHeader) {
        Long userId = Long.parseLong(userIdHeader);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new EntityNotFoundException("토큰 재발급 : 해당하는 유저가 없습니다 userId : " + userId));

        String newAccessToken = jwtUtil.createAccessToken(user.getId(), user.getUsername(), user.getRole().name());
        String newRefreshToken = jwtUtil.createRefreshToken(user.getId());

        long refreshTokenExpCheck = jwtUtil.getMilliSecond(newRefreshToken);

        // 화이트리스트에 등록할 새로운 리프레쉬 토큰을 위해 기존 화이트리스트에 저장된 리프레쉬 토큰 삭제
        stringRedisTemplate.delete("whitelist:RT:" + user.getId());

        // 기존 리프레쉬 토큰을 블랙리스트에 등록
        stringRedisTemplate.opsForValue().set(
            "blacklist:RT:" + oldRefreshToken,
            "TOKEN_REFRESH",
            refreshTokenExpCheck,
            TimeUnit.MILLISECONDS
        );

        // 새로운 리프레쉬 토큰을 화이트리스트에 등록
        stringRedisTemplate.opsForValue().set(
            "whitelist:RT:" + user.getId(),
            newRefreshToken,
            jwtRefreshExpTime,
            TimeUnit.MILLISECONDS
        );

        return AuthTokenRefreshResponseDto.builder()
            .newAccessToken(newAccessToken)
            .newRefreshToken(newRefreshToken)
            .build();
    }

    // 회원가입시 마스터가 생성하는 것을 가정하여 마스터의 userId를 생성자 필드에 넣을 수 있도록 메서드 제작. master 가 아니라면 기본값 0l 넣기
    private Long parseOrDefault(String header) {
        if (header == null) return 0L;
        try {
            return Long.valueOf(header);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

}
