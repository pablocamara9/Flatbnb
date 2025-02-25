package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.piso.GetAllPropietariosDto;
import com.salesianostriana.flatbnb.dto.propietario.EditPropietarioDto;
import com.salesianostriana.flatbnb.dto.propietario.GetPropietarioDto;
import com.salesianostriana.flatbnb.service.PropietarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario/")
public class PropietarioController {

    private final PropietarioService propietarioService;

    @GetMapping
    public ResponseEntity<GetAllPropietariosDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPropietariosDto.fromDto(propietarioService.findAll()));
    }

    @GetMapping("{id}")
    public GetPropietarioDto findById(@PathVariable UUID id) {
        return GetPropietarioDto.of(propietarioService.findById(id));
    }

    @PutMapping("{id}")
    public GetPropietarioDto edit(@PathVariable UUID id, @RequestBody EditPropietarioDto editPropietarioDto) {
        return GetPropietarioDto.of(propietarioService.edit(id, editPropietarioDto));
    }

}
