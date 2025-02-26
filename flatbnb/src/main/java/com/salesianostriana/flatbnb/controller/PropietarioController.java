package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.propietario.GetAllPropietariosDto;
import com.salesianostriana.flatbnb.dto.propietario.EditPropietarioDto;
import com.salesianostriana.flatbnb.dto.propietario.GetPropietarioDto;
import com.salesianostriana.flatbnb.dto.user.CreateUserDto;
import com.salesianostriana.flatbnb.model.Propietario;
import com.salesianostriana.flatbnb.service.PropietarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario/")
@Tag(name = "Propietario", description = "El controlador de los propietarios, para gestionar todas las operaciones relacionadas con ellos")
public class PropietarioController {

    private final PropietarioService propietarioService;

    @Operation(summary = "Obtiene todos los propietarios y los devuelve en forma de listado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los propietarios",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Propietario.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "9f1ec1a7-490e-4f0c-9c96-96849f14b7c8",
                                                "nombre": "Manolo",
                                                "apellidos": "García",
                                                "email": "manolo.garcia@gmail.com",
                                                "telefono": "954000000",
                                                "valoracion": 4.5
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los propietarios"
            )
    })
    @GetMapping
    public ResponseEntity<GetAllPropietariosDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPropietariosDto.fromDto(propietarioService.findAll()));
    }

    @Operation(summary = "Crea un nuevo propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Propietario creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para crear el propietario",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<GetPropietarioDto> create(@RequestBody CreateUserDto createUserDto) {
        Propietario propietario = propietarioService.create(createUserDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetPropietarioDto.of(propietario));
    }

    @Operation(summary = "Obtiene un propietario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el propietario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el propietario con el id (id proporcionado)",
                    content = @Content)
    })
    @GetMapping("{id}")
    public GetPropietarioDto findById(@PathVariable UUID id) {
        return GetPropietarioDto.of(propietarioService.findById(id));
    }

    @Operation(summary = "Edita un propietario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Propietario actualizado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el propietario con el id (id proporcionado)",
                    content = @Content)
    })
    @PutMapping("{id}")
    public GetPropietarioDto edit(@PathVariable UUID id, @RequestBody EditPropietarioDto editPropietarioDto) {
        return GetPropietarioDto.of(propietarioService.edit(id, editPropietarioDto));
    }

    @Operation(summary = "Elimina un propietario buscándolo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Propietario eliminado con éxito",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el propietario con el id (id proporcionado)",
                    content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        propietarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Consultas
    @Operation(summary = "Obtiene un un listado de propietarios ordenando por precio de menor a mayor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Propietarios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los propietarios",
                    content = @Content)
    })
    @GetMapping("/valoracion")
    public ResponseEntity<GetAllPropietariosDto> findAllOrderByValoracion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPropietariosDto.fromDto(propietarioService.findAllOrderByValoracion()));
    }

    @Operation(summary = "Obtiene un un listado de propietarios ordenando por valoración de mayor a menor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Propietarios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los propietarios",
                    content = @Content)
    })
    @GetMapping("/valoracionDesc")
    public ResponseEntity<GetAllPropietariosDto> findAllOrderByValoracionDesc() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPropietariosDto.fromDto(propietarioService.findAllOrderByValoracionDesc()));
    }

    @Operation(summary = "Obtiene un un listado de propietarios entre un rango de valoraciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Propietarios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los propietarios",
                    content = @Content)
    })
    @GetMapping("/valoracionBetween/{min}/{max}")
    public ResponseEntity<GetAllPropietariosDto> findAllByValoracionBetween(@PathVariable double min, @PathVariable double max) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPropietariosDto.fromDto(propietarioService.findAllByValoracionBetween(min, max)));
    }

    @Operation(summary = "Obtiene un un listado de propietarios con una valoracion mayor a la proporcionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Propietarios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los propietarios",
                    content = @Content)
    })
    @GetMapping("/valoracionMayor/{valoracion}")
    public ResponseEntity<GetAllPropietariosDto> findAllByValoracionGreaterThan(@PathVariable double valoracion) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPropietariosDto.fromDto(propietarioService.findAllByValoracionGreaterThan(valoracion)));
    }

    @Operation(summary = "Obtiene un un listado de propietarios con una valoracion mayor a la proporcionada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los Propietarios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los propietarios",
                    content = @Content)
    })
    @GetMapping("/valoracionMenor/{valoracion}")
    public ResponseEntity<GetAllPropietariosDto> findAllByValoracionLessThan(@PathVariable double valoracion) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPropietariosDto.fromDto(propietarioService.findAllByValoracionLessThan(valoracion)));
    }

}
