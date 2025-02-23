package com.salesianostriana.flatbnb.dto.user;

public record EditUserDto(
        String nombre,
        String apellidos,
        String email,
        String telefono,
        String password,
        String confirmPassword
) {
}
