package com.management_cars.demo_management_cars.service;

import com.management_cars.demo_management_cars.dto.mapper.CarMapper;
import com.management_cars.demo_management_cars.dto.response.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.entity.Car;
import com.management_cars.demo_management_cars.repository.CarRepository;
import com.management_cars.demo_management_cars.repository.specification.CarSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public Page<CarResponseDTO> getAll(String brand, String model, Integer year, Pageable pageable) {

        Specification<Car> spec = Specification
                .where(CarSpecification.brandLike(brand))
                .and(CarSpecification.modelLike(model))
                .and(CarSpecification.yearEquals(year));

        return carRepository.findAll(spec, pageable)
                .map(CarMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Car getById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));
    }

    @Transactional
    public Car update(Long id, Car car) {
        Car existing = getById(id);
        existing.setBrand(car.getBrand());
        existing.setModel(car.getModel());
        existing.setYear(car.getYear());
        return carRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Car car = getById(id);
        carRepository.delete(car);
    }
}
