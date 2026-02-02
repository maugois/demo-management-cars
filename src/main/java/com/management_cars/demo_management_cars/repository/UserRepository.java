package com.management_cars.demo_management_cars.repository;

import com.management_cars.demo_management_cars.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
