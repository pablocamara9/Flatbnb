package com.salesianostriana.flatbnb.dto.anuncio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditAnuncioDto(
        String descripcion,
        @NotNull(message = "El precio no puede estar vacío")
        @DecimalMin("0.01")
        double precio,
        String urlImagen,
        @NotNull(message = "El id del piso no puede estar vacío")
        UUID idPiso,
        @NotNull(message = "El id del propietario no puede estar vacío")
        UUID idPropietario
) {
}
