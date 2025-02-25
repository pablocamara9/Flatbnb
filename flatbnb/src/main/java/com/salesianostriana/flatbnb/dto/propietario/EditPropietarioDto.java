package com.salesianostriana.flatbnb.dto.propietario;


public record EditPropietarioDto(
        String nombre,
        String apellidos,
        String email,
        String telefono,
        String password,
        String confirmPassword,
        double valoracion
) {
}
