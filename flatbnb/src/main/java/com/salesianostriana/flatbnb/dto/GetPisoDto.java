package com.salesianostriana.flatbnb.dto;

import com.salesianostriana.flatbnb.model.Piso;
import lombok.Builder;

@Builder
public record GetPisoDto(
        String direccion,
        double metrosCuadrados,
        int numHabitaciones,
        String observaciones
) {
    public static GetPisoDto of(Piso piso) {
        return GetPisoDto.builder()
                .direccion(piso.getDireccion())
                .metrosCuadrados(piso.getMetrosCuadrados())
                .numHabitaciones(piso.getNumHabitaciones())
                .observaciones(piso.getObservaciones())
                .build();
    }
}
