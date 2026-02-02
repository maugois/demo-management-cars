package com.management_cars.demo_management_cars.service;

import com.management_cars.demo_management_cars.dto.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.entity.Car;
import com.management_cars.demo_management_cars.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public Car save(Car car) {
        try {
            return carRepository.save(car);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException();
        }
    }

    @Transactional(readOnly = true)
    public Page<CarResponseDTO> getAll (Pageable pageable) {
        return carRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Car getById (Car car) {
        return car;
    }

    @Transactional
    public Car update (Car car) {
        return car;
    }

    @Transactional
    public void delete () {

    }
}
