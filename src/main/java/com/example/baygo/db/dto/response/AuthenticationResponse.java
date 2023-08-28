package com.example.baygo.db.dto.response;

import com.example.baygo.db.model.enums.Role;
import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String email,
        Role role,
        String token,
        String message
) {
}
