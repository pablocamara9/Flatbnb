package com.salesianostriana.flatbnb.dto.anuncio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditAnuncioDto(
        @NotBlank(message = "{editAnuncioDto.descripcion.notblank}")
        String descripcion,
        @NotNull(message = "{editAnuncioDto.precio.notnull}")
        @DecimalMin(value = "0.01", message = "{editAnuncioDto.precio.min}")
        double precio,
        String urlImagen,
        @NotNull(message = "{editAnuncioDto.idPiso.notnull}")
        UUID idPiso,
        @NotNull(message = "{editAnuncioDto.idPropietario.notnull}")
        UUID idPropietario
) {
}
