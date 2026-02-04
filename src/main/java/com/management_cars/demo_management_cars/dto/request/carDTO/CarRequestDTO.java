package com.management_cars.demo_management_cars.dto.request.carDTO;

import com.management_cars.demo_management_cars.entity.Car;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CarRequestDTO(
        @NotBlank(message = "O modelo é obrigatório")
        String model,

        @NotBlank(message = "A marca é obrigatória")
        String brand,

        @NotBlank(message = "A cor é obrigatória")
        String color,

        @NotNull(message = "O ano é obrigatório")
        @Min(value = 1886, message = "Ano inválido")
        Integer year
) {
}
