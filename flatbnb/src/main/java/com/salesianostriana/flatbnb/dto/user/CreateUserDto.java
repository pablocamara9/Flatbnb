package com.salesianostriana.flatbnb.dto.user;

public record CreateUserDto(
        String username,
        String password,
        String nombre,
        String apellidos,
        String email,
        String telefono
) {
}
