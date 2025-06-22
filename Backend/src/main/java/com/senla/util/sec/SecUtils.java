package com.senla.util.sec;

import com.senla.dto.user.UserResponseDto;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecUtils {
    public static Long extractId(){
        return ((UserResponseDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId();
    }
}
