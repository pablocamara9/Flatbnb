package com.salesianostriana.flatbnb.dto;

public record EditUserDto(
        String nombre,
        String apellidos,
        String email,
        String telefono,
        String password,
        String confirmPassword
) {
}
