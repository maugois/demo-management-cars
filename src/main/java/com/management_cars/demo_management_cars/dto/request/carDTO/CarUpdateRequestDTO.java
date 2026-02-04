package com.management_cars.demo_management_cars.dto.request.carDTO;

import java.util.Optional;

public record CarUpdateRequestDTO(
        String model,
        String brand,
        String color,
        Integer year
) {
}
