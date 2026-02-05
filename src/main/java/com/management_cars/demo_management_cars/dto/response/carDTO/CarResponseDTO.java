package com.management_cars.demo_management_cars.dto.response.carDTO;

public record CarResponseDTO(
        Long id,
        String model,
        String brand,
        String color,
        Integer year
) {
}
