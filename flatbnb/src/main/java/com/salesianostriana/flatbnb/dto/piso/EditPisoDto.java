package com.salesianostriana.flatbnb.dto.piso;

import com.salesianostriana.flatbnb.dto.propietario.GetPropietarioDto;

public record EditPisoDto(
        String direccion,
        double metrosCuadrados,
        int numHabitaciones,
        String observaciones,
        GetPropietarioDto getPropietarioDto
) {
}
