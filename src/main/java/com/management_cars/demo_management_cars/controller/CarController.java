package com.management_cars.demo_management_cars.controller;

import com.management_cars.demo_management_cars.dto.carDTO.CarRequestDTO;
import com.management_cars.demo_management_cars.dto.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.dto.mapper.CarMapper;
import com.management_cars.demo_management_cars.dto.mapper.PageableMapper;
import com.management_cars.demo_management_cars.dto.pageableDTO.PageableDTO;
import com.management_cars.demo_management_cars.entity.Car;
import com.management_cars.demo_management_cars.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/carros")
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponseDTO> create (@Valid @RequestBody CarRequestDTO carRequestDTO) {
        Car car = carService.save(CarMapper.toCar(carRequestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(CarMapper.toDto(car));
    }

    @GetMapping
    public ResponseEntity<PageableDTO> getAll (@PageableDefault(size = 5, sort = {"model"}) Pageable pageable) {
        Page<CarResponseDTO> cars = carService.getAll(pageable);
        return ResponseEntity.ok(PageableMapper.toDto(cars));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CarResponseDTO> getById (@Valid @RequestBody Long idCar) {
//        Car car = carService.save(CarMapper.toCar(idCar));
//        return ResponseEntity.status(HttpStatus.CREATED).body(CarMapper.toDto(car));
//    }
//
//    @PostMapping
//    public ResponseEntity<CarResponseDTO> update (@Valid @RequestBody CarRequestDTO carRequestDTO) {
//        Car car = carService.save(CarMapper.toCar(carRequestDTO));
//        return ResponseEntity.status(HttpStatus.CREATED).body(CarMapper.toDto(car));
//    }
//
//    @PostMapping
//    public ResponseEntity<CarResponseDTO> delete (@Valid @RequestBody CarRequestDTO carRequestDTO) {
//        Car car = carService.save(CarMapper.toCar(carRequestDTO));
//        return ResponseEntity.status(HttpStatus.CREATED).body(CarMapper.toDto(car));
//    }
}
