package com.salesianostriana.flatbnb.dto.anuncio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateAnuncioDto(
        @NotBlank(message = "{createAnuncioDto.descripcion.notblank}")
        String descripcion,
        @NotNull(message = "{createAnuncioDto.precio.notnull}")
        @DecimalMin(value = "0.01", message = "{createAnuncioDto.precio.decimalmin}")
        double precio,
        String urlImagen,
        @NotNull(message = "{createAnuncioDto.idPiso.notnull}")
        UUID idPiso,
        @NotNull(message = "{createAnuncioDto.idPropietario.notnull}")
        UUID idPropietario
) {
}
