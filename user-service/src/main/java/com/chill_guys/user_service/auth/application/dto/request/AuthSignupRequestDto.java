package com.chill_guys.user_service.auth.application.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthSignupRequestDto {

    @Size(min = 4, max = 10, message = "username 은 최소 4자 이상, 10자 이하여야 합니다.")
    @Pattern(
        regexp = "^[a-z0-9]+$",
        message = "username 은 알파벳 소문자와 숫자로만 구성되어야 합니다."
    )
    private String username;

    @Size(min = 8, max = 15, message = "password 는 최소 8자 이상, 15자 이하여야 합니다.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`])[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$",
        message = "password 는 알파벳 대소문자, 숫자, 특수문자로만 구성되어야 합니다."
    )
    private String password;

    private String slackId;

}
