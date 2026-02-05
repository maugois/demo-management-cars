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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public Car save(Car car) {

        log.info("Criando carro: model={}, brand={}, year={}",
                car.getModel(), car.getBrand(), car.getYear());

        int yearMax = LocalDateTime.now().getYear() + 3;

        if (car.getYear() < 1850 || car.getYear() > yearMax) {
            log.warn("Ano inválido para carro: {}", car.getYear());
            throw new BadRequestException("Ano inválido");
        }

        try {
            return carRepository.save(car);
        } catch (Exception ex) {
            log.warn("Violação de integridade ao salvar carro", ex);

            throw new BadRequestException("Dados inválidos ou violação de integridade");
        }
    }

    @Transactional(readOnly = true)
    public Page<CarResponseDTO> getAll(String model, String brand, Integer year, Pageable pageable) {

        log.debug("Buscando carros com filtros: model={}, brand={}, year={}",
                model, brand, year);

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

        log.warn("Carro não encontrado: id={}", id);

        return carRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Carro com id " + id + " não encontrado")
        );
    }

    @Transactional
    public Car update(Long id, CarUpdateRequestDTO dto) {

        log.info("Atualizando carro: id={}", id);
        int yearMax = LocalDateTime.now().getYear() + 3;

        Car existing = getById(id);

        if (dto.brand() != null) {
            if (dto.brand().isBlank()) {
                log.warn("Marca vazia no update do carro id={}", id);
                throw new BadRequestException("Marca não pode ser vazia");
            }

            existing.setBrand(dto.brand());
        }

        if (dto.model() != null) {
            if (dto.model().isBlank()) {
                log.warn("Modelo vazio no update do carro id={}", id);
                throw new BadRequestException("Modelo não pode ser vazio");
            }

            existing.setModel(dto.model());
        }

        if (dto.color() != null) {
            if (dto.color().isBlank()) {
                log.warn("Cor vazia no update do carro id={}", id);
                throw new BadRequestException("Cor não pode ser vazia");
            }

            existing.setColor(dto.color());
        }

        if (dto.year() != null) {
            if (dto.year() < 1850 || dto.year() > yearMax) {
                log.warn("Ano inválido ({}) no update do carro id={}", dto.year(), id);
                throw new BadRequestException("Ano inválido");
            }

            existing.setYear(dto.year());
        }

        return carRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        log.info("Removendo carro: id={}", id);
        Car car = getById(id);
        carRepository.delete(car);
    }
}
