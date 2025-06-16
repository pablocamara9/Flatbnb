package com.salesianostriana.flatbnb.dto.user;

import com.salesianostriana.flatbnb.model.UserRole;

public record CreateUserDto(
        String username,
        String password,
        String nombre,
        String apellidos,
        String email,
        String telefono,
        UserRole role
) {
}
