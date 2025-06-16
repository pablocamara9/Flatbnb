package com.salesianostriana.flatbnb.dto.user;

import com.salesianostriana.flatbnb.model.UserRole;

public record EditUserDto(
        String nombre,
        String apellidos,
        String email,
        String telefono,
        UserRole role
        /*String password,
        String confirmPassword*/
) {
}
