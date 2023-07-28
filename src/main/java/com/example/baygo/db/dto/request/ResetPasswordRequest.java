package com.example.baygo.db.dto.request;

import com.example.baygo.validations.PasswordValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ResetPasswordRequest (
        @NotBlank(message = "The password must not be empty.")
        @PasswordValid
        String newPassword
){
}
