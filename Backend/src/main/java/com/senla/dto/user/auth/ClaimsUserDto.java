package com.senla.dto.user.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.model.user.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClaimsUserDto {

    @JsonProperty("user_id")
    private Long userId;

    private String email;

    private String role;

    @NotNull
    @JsonProperty("name")
    private String firstName;

    @JsonProperty("surname")
    private String lastName;

    @NotNull
    @JsonProperty("phone_number")
    private String phoneNumber;

}