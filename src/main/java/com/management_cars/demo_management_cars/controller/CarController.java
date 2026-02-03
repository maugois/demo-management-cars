package com.management_cars.demo_management_cars.controller;

import com.management_cars.demo_management_cars.dto.mapper.CarMapper;
import com.management_cars.demo_management_cars.dto.request.carDTO.CarRequestDTO;
import com.management_cars.demo_management_cars.dto.request.carDTO.CarUpdateDTO;
import com.management_cars.demo_management_cars.dto.response.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.entity.Car;
import com.management_cars.demo_management_cars.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/carros")
public class CarController {

    private final CarService carService;

    @PostMapping // Certo: falta exceções
    public ResponseEntity<CarResponseDTO> create (@RequestBody @Valid CarRequestDTO dto) {
        Car car = carService.save(CarMapper.toCar(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CarMapper.toDto(car));
    }

    @GetMapping // Certo: falta exceções
    public ResponseEntity<Page<CarResponseDTO>> getAll(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer year,
            @PageableDefault(
                    size = 5, sort = "brand",
                    direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<CarResponseDTO> page = carService.getAll(model, brand, year, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> getById(@PathVariable Long id) {
        Car car = carService.getById(id);
        return ResponseEntity.ok(CarMapper.toDto(car));
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<CarResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CarRequestDTO dto) {
//        Car car = carService.update(id, CarMapper.toCar(dto));
//        return ResponseEntity.ok(CarMapper.toDto(car));
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
