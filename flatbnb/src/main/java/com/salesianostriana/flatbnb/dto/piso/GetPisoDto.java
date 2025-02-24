package com.salesianostriana.flatbnb.dto.piso;

import com.salesianostriana.flatbnb.model.Piso;
import lombok.Builder;

import java.util.UUID;

@Builder
public record GetPisoDto(
        UUID id,
        String direccion,
        double metrosCuadrados,
        int numHabitaciones,
        String observaciones
) {
    public static GetPisoDto of(Piso piso) {
        return GetPisoDto.builder()
                .id(piso.getId())
                .direccion(piso.getDireccion())
                .metrosCuadrados(piso.getMetrosCuadrados())
                .numHabitaciones(piso.getNumHabitaciones())
                .observaciones(piso.getObservaciones())
                .build();
    }
}
