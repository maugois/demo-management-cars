package com.management_cars.demo_management_cars.controller;

import com.management_cars.demo_management_cars.dto.mapper.CarMapper;
import com.management_cars.demo_management_cars.dto.request.carDTO.CarRequestDTO;
import com.management_cars.demo_management_cars.dto.request.carDTO.CarUpdateRequestDTO;
import com.management_cars.demo_management_cars.dto.response.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.entity.Car;
import com.management_cars.demo_management_cars.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cars")
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarResponseDTO> create (@RequestBody @Valid CarRequestDTO dto) {
        log.debug("POST /api/v1/carros");
        Car car = carService.save(CarMapper.toCar(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CarMapper.toDto(car));
    }


    @GetMapping
    public ResponseEntity<Page<CarResponseDTO>> getAll(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer year,
            @PageableDefault(
                    size = 5, sort = "brand",
                    direction = Sort.Direction.ASC) Pageable pageable
    ) {
        log.debug("GET /api/v1/carros");
        Page<CarResponseDTO> page = carService.getAll(model, brand, year, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> getById(@PathVariable Long id) {
        log.debug("GET /api/v1/carros/{}", id);
        Car car = carService.getById(id);
        return ResponseEntity.ok(CarMapper.toDto(car));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDTO> update(
            @PathVariable Long id,
            @RequestBody CarUpdateRequestDTO dto
    ) {
        log.debug("PUT /api/v1/carros/{}", id);
        Car car = carService.update(id, dto);
        return ResponseEntity.ok(CarMapper.toDto(car));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("DELETE /api/v1/carros/{}", id);
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
