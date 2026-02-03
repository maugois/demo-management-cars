package com.management_cars.demo_management_cars.dto.request.carDTO;

import java.util.Optional;

public record CarUpdateDTO(
        Optional<String> model,
        Optional<String> brand,
        Optional<String> color,
        Optional<Integer> year
) {
}
