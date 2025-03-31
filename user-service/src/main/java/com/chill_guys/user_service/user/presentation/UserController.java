package com.chill_guys.user_service.user.presentation;

import com.chill_guys.user_service.user.application.dto.request.UserPasswordUpdateRequestDto;
import com.chill_guys.user_service.user.application.dto.request.UserRoleUpdateRequestDto;
import com.chill_guys.user_service.user.application.dto.request.UserUpdateRequestDto;
import com.chill_guys.user_service.user.application.dto.response.UserRoleSearchResponseDto;
import com.chill_guys.user_service.user.application.dto.response.UserRoleUpdateResponseDto;
import com.chill_guys.user_service.user.application.dto.response.UserSearchAllResponseDto;
import com.chill_guys.user_service.user.application.dto.response.UserSearchMeResponseDto;
import com.chill_guys.user_service.user.application.dto.response.UserSearchResponseDto;
import com.chill_guys.user_service.user.application.dto.response.UserUpdateResponseDto;
import com.chill_guys.user_service.user.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "UserController")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 유저 검색 : 본인
    @GetMapping("/users/me")
    public ResponseEntity<UserSearchMeResponseDto> getUserMe(@RequestHeader(value = "X-User-Id", required = false) String userIdHeader
    ) {
        UserSearchMeResponseDto responseDto = userService.searchMeUser(userIdHeader);
        return ResponseEntity.ok(responseDto);
    }

    // MASTER : 모든 유저 검색
    @GetMapping("/master/users/all")
    public ResponseEntity<Page<UserSearchAllResponseDto>> searchAllUser(Pageable pageable
    ) {
        Page<UserSearchAllResponseDto> responseDto = userService.searchAllUser(pageable);

        return ResponseEntity.ok(responseDto);
    }

    // MASTER : 유저 검색 : userId, username 기반. 동시 검색 X
    @GetMapping("/master/users/search")
    public ResponseEntity<UserSearchResponseDto> searchUser(@RequestParam(required = false) Long userId,
                                                            @RequestParam(required = false) String username
    ) {

        if (username == null && userId == null) {
            log.warn("username 과 userId가 동시에 입력되었습니다.");
            return ResponseEntity.badRequest().build();

        } else if (username != null && userId != null) {
            log.warn("username 과 userId가 비어있습니다.");
            return ResponseEntity.badRequest().build();
        }

        UserSearchResponseDto responseDto = userService.searchUser(userId, username);
        return ResponseEntity.ok(responseDto);
    }

    // MASTER : 유저 권한 기반 조회.
    @GetMapping("/master/users/role")
    public ResponseEntity<Page<UserRoleSearchResponseDto>> roleSearchUser(@RequestParam(required = true) String userRole,
                                                                          Pageable pageable
    ) {
        Page<UserRoleSearchResponseDto> responseDtoList = userService.roleSearchUser(userRole, pageable);
        return ResponseEntity.ok(responseDtoList);
    }

    // 유저 수정 : 본인 비밀번호 수정
    @PutMapping("/users/password")
    public ResponseEntity<String> updatePassword(@RequestHeader(value = "X-User-Id") String userIdHeader,
                                                 @Valid @RequestBody UserPasswordUpdateRequestDto requestDto
    ) {
        userService.updatePassword(userIdHeader, requestDto);
        return ResponseEntity.ok("비밀번호가 변경되었습니다.");
    }

    // MASTER : 유저 프로필 수정
    @PutMapping("/master/users/{userId}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable("userId") Long userId,
                                                            @RequestHeader(value = "X-User-Id") String userIdHeader,
                                                            @Valid @RequestBody UserUpdateRequestDto requestDto
    ) {
        UserUpdateResponseDto responseDto = userService.updateUser(userId, requestDto, userIdHeader);
        return ResponseEntity.ok(responseDto);
    }

    // MASTER : 유저 권한 수정
    @PutMapping("/master/users/role/{userId}")
    public ResponseEntity<UserRoleUpdateResponseDto> roleUpdateUser(@PathVariable("userId") Long userId,
                                                                    @RequestHeader(value = "X-User-Id") String userIdHeader,
                                                                    @RequestBody UserRoleUpdateRequestDto requestDto
    ) {
        UserRoleUpdateResponseDto responseDto = userService.roleUpdateUser(userId, userIdHeader, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    // MASTER : 회원 탈퇴
    @DeleteMapping("/master/users/{userId}")
    public ResponseEntity<String> deleteUser(@RequestHeader(value = "X-User-Id") String userIdHeader,
                                             @PathVariable("userId") Long userId
    ) {
        userService.deleteUser(userIdHeader, userId);
        return ResponseEntity.ok("유저가 삭제되었습니다 userId : " + userId);
    }

}
