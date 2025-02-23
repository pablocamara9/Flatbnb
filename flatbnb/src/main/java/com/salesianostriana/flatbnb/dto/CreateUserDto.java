package com.salesianostriana.flatbnb.dto;

public record CreateUserDto(
        String username,
        String password,
        String confirmPassword,
        String nombre,
        String apellidos,
        String email,
        String telefono
) {
}
