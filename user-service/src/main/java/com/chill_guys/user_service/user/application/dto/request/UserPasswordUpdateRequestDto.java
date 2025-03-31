package com.chill_guys.user_service.user.application.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserPasswordUpdateRequestDto {

    @Size(min = 8, max = 15, message = "old password 는 최소 8자 이상, 15자 이하여야 합니다.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`])[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$",
        message = "old password 는 알파벳 대소문자, 숫자, 특수문자로만 구성되어야 합니다."
    )
    private String oldPassword;

    @Size(min = 8, max = 15, message = "new password 는 최소 8자 이상, 15자 이하여야 합니다.")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`])[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~`]+$",
        message = "new password 는 알파벳 대소문자, 숫자, 특수문자로만 구성되어야 합니다."
    )
    private String newPassword;
}
