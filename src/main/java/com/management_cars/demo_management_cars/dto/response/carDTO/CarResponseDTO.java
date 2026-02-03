package com.management_cars.demo_management_cars.dto.response.carDTO;

import java.util.Date;

public record CarResponseDTO(

        String model,
        String brand,
        String color,
        Date year
) {
}
