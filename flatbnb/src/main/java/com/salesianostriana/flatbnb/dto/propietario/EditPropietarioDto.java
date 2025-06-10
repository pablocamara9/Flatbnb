package com.salesianostriana.flatbnb.dto.propietario;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EditPropietarioDto(
        @NotBlank(message = "{editPropietarioDto.nombre.notblank}")
        String nombre,
        @NotBlank(message = "{editPropietarioDto.apellidos.notblank}")
        String apellidos,
        @NotBlank(message = "{editPropietarioDto.email.notblank}")
        String email,
        @NotBlank(message = "{editPropietarioDto.telefono.notblank}")
        @Size(min = 9, max = 12, message = "{editPropietarioDto.telefono.size}")
        String telefono,
        @NotBlank(message = "{editPropietarioDto.password.notblank}")
        String password,
        double valoracion
) {
}
