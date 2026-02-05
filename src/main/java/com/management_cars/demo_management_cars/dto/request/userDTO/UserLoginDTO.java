package com.management_cars.demo_management_cars.dto.request.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDTO(
        @NotBlank(message = "O email é obrigatória")
        @Email(message = "O e-mail não está no formato ideal", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
        String email,

        @NotBlank(message = "A senha  é obrigatória")
        @Size(min = 2, message = "Mínimo de caracteres é 2")
        String password
) {
}
