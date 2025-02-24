package com.salesianostriana.flatbnb.dto.piso;

public record CreatePisoDto(
        String direccion,
        double metrosCuadrados,
        int numHabitaciones,
        String observaciones
) {
}
