package com.salesianostriana.flatbnb.dto.propietario;

import com.salesianostriana.flatbnb.model.Propietario;
import lombok.Builder;

import java.util.UUID;

@Builder
public record GetPropietarioDto(
        String nombre,
        String apellidos,
        String email,
        String telefono,
         double valoracion
) {
    public static GetPropietarioDto of(Propietario propietario) {
        return GetPropietarioDto.builder()
                .nombre(propietario.getNombre())
                .apellidos(propietario.getApellidos())
                .email(propietario.getEmail())
                .telefono(propietario.getTelefono())
                .valoracion(propietario.getValoracion())
                .build();
    }

}
