package com.salesianostriana.flatbnb.dto.piso;

public record EditPisoDto(
        String direccion,
        double metrosCuadrados,
        int numHabitaciones,
        String observaciones
) {
}
