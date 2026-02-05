package com.management_cars.demo_management_cars.dto.mapper;

import com.management_cars.demo_management_cars.dto.request.carDTO.CarRequestDTO;
import com.management_cars.demo_management_cars.dto.response.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.entity.Car;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarMapper {
    public static Car toCar(CarRequestDTO dto) {
            Car car = new Car();
            car.setModel(dto.model());
            car.setBrand(dto.brand());
            car.setColor(dto.color());
            car.setYear(dto.year());
            return car;
    }

    public static CarResponseDTO toDto(Car car) {
        return new CarResponseDTO(
                car.getIdCar(),
                car.getModel(),
                car.getBrand(),
                car.getColor(),
                car.getYear()
        );
    }
}
