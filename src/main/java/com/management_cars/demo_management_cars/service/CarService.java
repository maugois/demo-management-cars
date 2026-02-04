package com.management_cars.demo_management_cars.service;

import com.management_cars.demo_management_cars.dto.mapper.CarMapper;
import com.management_cars.demo_management_cars.dto.request.carDTO.CarUpdateRequestDTO;
import com.management_cars.demo_management_cars.dto.response.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.entity.Car;
import com.management_cars.demo_management_cars.exception.BadRequestException;
import com.management_cars.demo_management_cars.exception.EntityNotFoundException;
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
            throw new BadRequestException("Dados inválidos ou violação de integridade");
        }
    }

    @Transactional(readOnly = true)
    public Page<CarResponseDTO> getAll(String model, String brand, Integer year, Pageable pageable) {

        Specification<Car> spec = CarSpecification.filter(brand, model, year);
        Page<Car> cars;

        if (spec == null) {
            cars = carRepository.findAll(pageable);
        } else {
            cars = carRepository.findAll(spec, pageable);
        }

        return cars.map(CarMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Car getById(Long id) {
        return carRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Carro com id " + id + " não encontrado")
        );
    }

    @Transactional
    public Car update(Long id, CarUpdateRequestDTO dto) {
        Car existing = getById(id);

        if (dto.brand() != null) {
            if (dto.brand().isBlank())
                throw new BadRequestException("Marca não pode ser vazia");
            existing.setBrand(dto.brand());
        }

        if (dto.model() != null) {
            if (dto.model().isBlank())
                throw new BadRequestException("Modelo não pode ser vazio");
            existing.setModel(dto.model());
        }

        if (dto.color() != null) {
            if (dto.color().isBlank())
                throw new BadRequestException("Cor não pode ser vazia");
            existing.setColor(dto.color());
        }

        if (dto.year() != null) {
            if (dto.year() < 1886)
                throw new BadRequestException("Ano inválido");
            existing.setYear(dto.year());
        }

        return carRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Car car = getById(id);
        carRepository.delete(car);
    }
}
