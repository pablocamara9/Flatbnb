package com.salesianostriana.flatbnb.dto.piso;

import java.util.UUID;

public record CreatePisoDto(
        String direccion,
        double metrosCuadrados,
        int numHabitaciones,
        String observaciones,
        UUID idPropietario
) {
}
