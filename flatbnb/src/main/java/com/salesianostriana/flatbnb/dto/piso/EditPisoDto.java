package com.salesianostriana.flatbnb.dto.piso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditPisoDto(
        @NotBlank(message = "{editPisoDto.direccion.notblank}")
        String direccion,
        @NotNull(message = "{editPisoDto.metrosCuadrados.notblank}")
        double metrosCuadrados,
        @NotNull(message = "{editPisoDto.numHabitaciones.notblank}")
        int numHabitaciones,
        String observaciones,
        @NotNull(message = "{editPisoDto.idPropietario.notnull}")
        UUID idPropietario
) {
}
