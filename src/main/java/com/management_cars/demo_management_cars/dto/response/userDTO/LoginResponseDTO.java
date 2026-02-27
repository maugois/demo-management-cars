package com.management_cars.demo_management_cars.dto.response.userDTO;

public record LoginResponseDTO(
        Long id,
        String name,
        String email,
        String token,
        long expiresIn
) {
}
