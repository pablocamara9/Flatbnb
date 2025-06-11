package com.salesianostriana.flatbnb.dto.piso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePisoDto(
        @NotBlank(message = "{createPisoDto.direccion.notblank}")
        String direccion,
        @NotBlank(message = "{createPisoDto.metrosCuadrados.notblank}")
        double metrosCuadrados,
        @NotBlank(message = "{createPisoDto.numHabitaciones.notblank}")
        int numHabitaciones,
        String observaciones,
        @NotNull(message = "{createPisoDto.idPropietario.notnull}")
        UUID idPropietario
) {
}
