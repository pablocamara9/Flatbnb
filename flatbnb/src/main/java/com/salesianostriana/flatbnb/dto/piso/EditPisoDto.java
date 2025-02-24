package com.salesianostriana.flatbnb.dto.piso;

import java.util.UUID;

public record EditPisoDto(
        String direccion,
        double metrosCuadrados,
        int numHabitaciones,
        String observaciones,
        UUID idPropietario
) {
}
