package com.management_cars.demo_management_cars.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car extends AuditableEntity implements Serializable {

    // Colunas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCar;

    @Column(name = "model", nullable = false, length = 100)
    private String model;

    @Column(name = "brand", nullable = false, length = 100)
    private String brand;

    @Column(name = "color", nullable = false, length = 100)
    private String color;

    @Column(name = "year", nullable = false, length = 100)
    private Date year;

    // Metódos
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(idCar, car.idCar) && Objects.equals(model, car.model) && Objects.equals(brand, car.brand) && Objects.equals(color, car.color) && Objects.equals(year, car.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCar, model, brand, color, year);
    }

    @Override
    public String toString() {
        return "Car{" +
                "idCar=" + idCar +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", year=" + year +
                '}';
    }
}
