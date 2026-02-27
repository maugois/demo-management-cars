package com.management_cars.demo_management_cars.controller;

import com.management_cars.demo_management_cars.dto.mapper.UserMapper;
import com.management_cars.demo_management_cars.dto.request.userDTO.UserLoginDTO;
import com.management_cars.demo_management_cars.dto.request.userDTO.UserRequestDTO;
import com.management_cars.demo_management_cars.dto.response.userDTO.LoginResponseDTO;
import com.management_cars.demo_management_cars.dto.response.userDTO.UserResponseDTO;
import com.management_cars.demo_management_cars.entity.User;
import com.management_cars.demo_management_cars.exception.ErrorResponse;
import com.management_cars.demo_management_cars.repository.UserRepository;
import com.management_cars.demo_management_cars.security.CustomUserDetailsService;
import com.management_cars.demo_management_cars.service.UserService;
import com.management_cars.demo_management_cars.utils.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create (@RequestBody @Valid UserRequestDTO dto) {
        log.debug("POST /api/v1/usuarios");
        User user  = userService.save(UserMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> auth (@RequestBody @Valid UserLoginDTO dto, HttpServletRequest request) {
        log.debug("POST /api/v1/usuarios/auth");

        try {
            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(dto.email(), dto.password());

            authenticationManager.authenticate(authRequest);
            String token = customUserDetailsService.getTokenAuthenticated(dto.email());
            var user = userRepository.findByEmail(dto.email()).orElseThrow();

            LoginResponseDTO response = new LoginResponseDTO(
                    user.getIdUser(),
                    user.getName(),
                    user.getEmail(),
                    token,
                    JwtTokenUtils.getExpirationTimeInSeconds()
            );

            return ResponseEntity.ok(response);

        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials from email '{}'", dto.email());
        }

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}
