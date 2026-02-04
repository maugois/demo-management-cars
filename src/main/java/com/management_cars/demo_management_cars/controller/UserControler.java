package com.management_cars.demo_management_cars.controller;

import com.management_cars.demo_management_cars.dto.mapper.UserMapper;
import com.management_cars.demo_management_cars.dto.request.userDTO.UserRequestDTO;
import com.management_cars.demo_management_cars.dto.response.userDTO.UserResponseDTO;
import com.management_cars.demo_management_cars.entity.User;
import com.management_cars.demo_management_cars.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserControler {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create (@RequestBody @Valid UserRequestDTO dto) {
        log.debug("POST /api/v1/usuarios");
        User user  = userService.save(UserMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @PostMapping("/auth")
    public ResponseEntity<UserResponseDTO> auth (@RequestBody @Valid UserRequestDTO dto) {
        log.debug("POST /api/v1/usuarios/auth");
        User user  = userService.authenticate(UserMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }
}
