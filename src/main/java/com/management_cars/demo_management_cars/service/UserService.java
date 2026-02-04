package com.management_cars.demo_management_cars.service;

import com.management_cars.demo_management_cars.entity.User;
import com.management_cars.demo_management_cars.exception.BadRequestException;
import com.management_cars.demo_management_cars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        log.info("Criando usuário: name={}, email={}, password={}",
                user.getName(), user.getEmail(), user.getPassword());

        try {
            return userRepository.save(user);

        } catch (Exception ex) {
            log.warn("Violação de integridade ao salvar usuaário", ex);

            throw new BadRequestException("Dados inválidos ou violação de integridade");
        }
    }

    public User authenticate(User user) {
        return user;
    }
}
