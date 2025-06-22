package com.senla.authpoint;


import com.senla.dto.user.UserResponseWithRoleDto;
import com.senla.dto.user.auth.*;
import com.senla.model.user.User;
import com.senla.util.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/signup")
    public ResponseEntity<UserWithTokenResponse> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        User registeredUser = authService.signup(registerUserDto);
        String jwtToken = jwtUtils.generateSimpleJwt(convertToClaimsUserDto(registeredUser));

        return ResponseEntity.ok(convertToUserResponseDto(registeredUser, jwtToken));
    }

    @PostMapping("/login")
    public ResponseEntity<UserWithTokenResponse> authenticate(@RequestBody @Valid LoginUserDto loginUserDto) {
        User authenticatedUser = authService.login(loginUserDto);
        String jwtToken = jwtUtils.generateSimpleJwt(convertToClaimsUserDto(authenticatedUser));

        return ResponseEntity.ok(convertToUserResponseDto(authenticatedUser, jwtToken));
    }

    private UserWithTokenResponse convertToUserResponseDto(User user, String jwtToken){
        var userDto = mapper.map(user, UserResponseWithRoleDto.class);
        userDto.setRole(user.getRole().getRoleName().toLowerCase());

        return UserWithTokenResponse.builder()
                    .user(userDto)
                    .token(jwtToken)
                    .build();
    }

    private ClaimsUserDto convertToClaimsUserDto(User registeredUser){
        var claimsDto = modelMapper.map(registeredUser, ClaimsUserDto.class);
        claimsDto.setRole(registeredUser.getRole().getRoleName().toLowerCase());
        return claimsDto;
    }
}
