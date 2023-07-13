package com.example.baygo.db.dto.request.auth;

import com.example.baygo.db.validations.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ResetPasswordRequest (
        @NotBlank(message = "The password must not be empty.")
        @NotNull(message = "The password must not be empty.")
        @PasswordValid
        String newPassword
){
}
