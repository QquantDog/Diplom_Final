package com.senla.config.mapper;

import com.senla.dto.user.UserResponseWithRoleDto;
import com.senla.dto.user.auth.ClaimsUserDto;
import com.senla.model.role.Role;
import com.senla.model.user.User;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyMappings {
    @Bean
    public PropertyMap<User, ClaimsUserDto> userMapping() {
        Converter<Role, String> roleConverter =
                ctx -> ctx.getSource().getRoleName().toLowerCase();

        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(roleConverter).map(source.getRole(), destination.getRole());
            }
        };
    }

    @Bean
    public PropertyMap<User, UserResponseWithRoleDto> userMapping2() {
        Converter<Role, String> roleConverter =
                ctx -> ctx.getSource().getRoleName().toLowerCase();

        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(roleConverter).map(source.getRole(), destination.getRole());
            }
        };
    }
}
