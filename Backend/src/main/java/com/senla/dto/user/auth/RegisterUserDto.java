package com.senla.dto.user.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserDto {

    @NotNull
    @JsonProperty("name")
    private String firstName;

    @JsonProperty("surname")
    private String lastName;

    @NotNull
    @JsonProperty("email")
    private String email;

    @NotNull
    @JsonProperty("password")
    private String password;

    @NotNull
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role_id")
    private Long roleId;
}
