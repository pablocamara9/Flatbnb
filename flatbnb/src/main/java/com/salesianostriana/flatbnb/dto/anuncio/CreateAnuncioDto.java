package com.salesianostriana.flatbnb.dto.anuncio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAnuncioDto(
        String descripcion,
        @NotNull(message = "{createAnuncioDto.precio.notnull}")
        @DecimalMin(value = "0.01", message = "{createAnuncioDto.precio.min}")
        double precio,
        String urlImagen,
        @NotNull(message = "{createAnuncioDto.idPiso.notnull}")
        UUID idPiso,
        @NotNull(message = "{createAnuncioDto.idPropietario.notnull}")
        UUID idPropietario
) {
}
