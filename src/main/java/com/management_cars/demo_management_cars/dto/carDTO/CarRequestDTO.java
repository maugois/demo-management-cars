package com.management_cars.demo_management_cars.dto.carDTO;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record CarRequestDTO(
        @NotBlank
        String model,

        @NotBlank
        String brand,

        @NotBlank
        String color,

        @NotBlank
        Date year
) {
}
