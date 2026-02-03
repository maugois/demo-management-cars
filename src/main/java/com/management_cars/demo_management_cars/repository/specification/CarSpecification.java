package com.management_cars.demo_management_cars.repository.specification;

import com.management_cars.demo_management_cars.entity.Car;
import org.springframework.data.jpa.domain.Specification;

public class CarSpecification {

    public static Specification<Car> filter(String brand, String model, Integer year) {

        Specification<Car> spec = (root, query, cb) -> cb.conjunction();

        if (brand != null && !brand.isBlank()) {
            spec = spec.and(brandLike(brand));
        }

        if (model != null && !model.isBlank()) {
            spec = spec.and(modelLike(model));
        }

        if (year != null) {
            spec = spec.and(yearEquals(year));
        }

        return spec;
    }

    public static Specification<Car> brandLike(String brand) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    public static Specification<Car> modelLike(String model) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("model")), "%" + model.toLowerCase() + "%");
    }

    public static Specification<Car> yearEquals(Integer year) {
        return (root, query, cb) ->
                cb.equal(root.get("year"), year);
    }
}
