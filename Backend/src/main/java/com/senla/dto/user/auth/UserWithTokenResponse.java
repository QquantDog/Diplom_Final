package com.senla.dto.user.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.user.UserResponseWithRoleDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserWithTokenResponse {

    @NotNull
    @JsonProperty("token")
    private String token;

    @NotNull
    @JsonProperty("user")
    private UserResponseWithRoleDto user;
}
