package com.management_cars.demo_management_cars.dto.mapper;

import com.management_cars.demo_management_cars.dto.request.userDTO.UserRequestDTO;
import com.management_cars.demo_management_cars.dto.response.userDTO.UserResponseDTO;
import com.management_cars.demo_management_cars.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User toUser(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }

    public static UserResponseDTO toDto(User user) {
        return new UserResponseDTO(
                user.getName(),
                user.getEmail(),
                user.getPassword()
        );
    }
}
