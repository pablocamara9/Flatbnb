package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.piso.CreatePisoDto;
import com.salesianostriana.flatbnb.dto.piso.EditPisoDto;
import com.salesianostriana.flatbnb.dto.piso.GetAllPisosDto;
import com.salesianostriana.flatbnb.dto.piso.GetPisoDto;
import com.salesianostriana.flatbnb.model.Piso;
import com.salesianostriana.flatbnb.service.PisoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/piso/")
@Tag(name = "Piso", description = "El controlador de los pisos, para gestionar todas las operaciones relacionadas con ellos")
public class PisoController {

    private final PisoService pisoService;

    @Operation(summary = "Obtiene todos los pisos y los devuelve en forma de listado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los pisos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Piso.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "60881ddb-743d-41d6-946b-159c7d41c7f1",
                                                "direccion": "Rue del Percebe, 13",
                                                "metrosCuadrados": 111.111,
                                                "numHabitaciones": 6,
                                                "observaciones": "Mu bonito olee oleee"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los pisos"
            )
    })
    @GetMapping
    public ResponseEntity<GetAllPisosDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPisosDto.fromDto(pisoService.findAll()));
    }

    @Operation(summary = "Crea un nuevo piso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Piso creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Piso.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para crear el piso",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<GetPisoDto> create(@RequestBody @Valid CreatePisoDto dto) {
        Piso piso = pisoService.createPiso(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetPisoDto.of(piso));
    }

    @Operation(summary = "Obtiene un piso por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el Piso",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Piso.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el piso con el id (id proporcionado)",
                    content = @Content)
    })
    @GetMapping("{id}")
    public GetPisoDto findById(@PathVariable UUID id) {
        return GetPisoDto.of(pisoService.findById(id));
    }

    @Operation(summary = "Edita un piso existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Piso actualizado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Piso.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el piso con el id (id proporcionado)",
                    content = @Content)
    })
    @PutMapping("{id}")
    public GetPisoDto edit(@PathVariable UUID id, @RequestBody @Valid EditPisoDto editPisoDto) {
        return GetPisoDto.of(pisoService.edit(id, editPisoDto));
    }

    @Operation(summary = "Elimina un piso buscándolo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Piso eliminado con éxito",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el piso con el id (id proporcionado)",
                    content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        pisoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
