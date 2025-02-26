package com.salesianostriana.flatbnb.dto.piso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditPisoDto(
        @NotBlank(message = "La dirección no puede estar vacía")
        String direccion,
        double metrosCuadrados,
        int numHabitaciones,
        String observaciones,
        @NotNull(message = "El id del propietario no puede estar vacío")
        UUID idPropietario
) {
}
