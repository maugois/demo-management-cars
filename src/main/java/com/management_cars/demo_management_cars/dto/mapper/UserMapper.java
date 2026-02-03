package com.management_cars.demo_management_cars.dto.mapper;

import com.management_cars.demo_management_cars.dto.request.userDTO.UserRequestDTO;
import com.management_cars.demo_management_cars.dto.response.userDTO.UserResponseDTO;
import com.management_cars.demo_management_cars.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User toUser(UserRequestDTO dto) {
        return new ModelMapper().map(dto, User.class);
    }

    public static UserResponseDTO toDto(User user) {
        return new ModelMapper().map(user, UserResponseDTO.class);
    }
}
