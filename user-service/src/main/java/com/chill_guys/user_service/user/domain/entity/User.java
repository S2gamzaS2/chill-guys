package com.chill_guys.user_service.user.domain.entity;

import com.chill_guys.user_service.auth.application.dto.request.AuthSignupRequestDto;
import com.chill_guys.user_service.user.application.dto.request.UserPasswordUpdateRequestDto;
import com.chill_guys.user_service.user.application.dto.request.UserRoleUpdateRequestDto;
import com.chill_guys.user_service.user.application.dto.request.UserUpdateRequestDto;
import com.chill_guys.user_service.global.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_user")
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true)
    @Size(min = 4, max = 10, message = "new username 은 최소 4자 이상, 10자 이하여야 합니다.")
    @Pattern(
        regexp = "^[a-z0-9]+$",
        message = "new username 은 알파벳 소문자와 숫자로만 구성되어야 합니다."
    )
    private String username;

    @Column(name = "slack_id", unique = true)
    private String slackId;

    @Column(name = "password")
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static User fromRequest(AuthSignupRequestDto requestDto) {
        return User.builder()
            .username(requestDto.getUsername())
            .password(BCrypt.hashpw(requestDto.getPassword(), BCrypt.gensalt()))
            .slackId(requestDto.getSlackId())
            .role(UserRole.ROLE_USER)
            .build();
    }

    public void updatePassword(UserPasswordUpdateRequestDto requestDto) {
        this.password = BCrypt.hashpw(requestDto.getNewPassword(), BCrypt.gensalt());
    }

    public void updateRole(UserRoleUpdateRequestDto requestDto) {
        if (requestDto.getNewRole() != null && !requestDto.getNewRole().isEmpty()) {
            try {
                this.role = UserRole.valueOf(requestDto.getNewRole());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("권한 변경에 잘못된 ROLE 값이 들어왔습니다. 받아온 ROLE : " + requestDto.getNewRole());
            }
        }
    }

    public void updateUser(UserUpdateRequestDto requestDto) {
        if (requestDto.getNewUsername() != null && !requestDto.getNewUsername().isEmpty()) {
            this.username = requestDto.getNewUsername();
        }
        if (requestDto.getNewSlackId() != null && !requestDto.getNewSlackId().isEmpty()) {
            this.slackId = requestDto.getNewSlackId();
        }
    }

}
