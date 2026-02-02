package com.management_cars.demo_management_cars.repository;

import com.management_cars.demo_management_cars.dto.carDTO.CarResponseDTO;
import com.management_cars.demo_management_cars.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car c")
    Page<CarResponseDTO> findAllPageable(Pageable pageable);
}
