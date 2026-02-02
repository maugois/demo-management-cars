package com.management_cars.demo_management_cars.dto.mapper;

import com.management_cars.demo_management_cars.dto.carDTO.CarRequestDTO;
import com.management_cars.demo_management_cars.dto.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.entity.Car;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarMapper {
    public static Car toCar(CarRequestDTO dto) {
        return new ModelMapper().map(dto, Car.class);
    }

    public static CarResponseDTO toDto(Car car) {
        return new ModelMapper().map(car, CarResponseDTO.class);
    }
}
