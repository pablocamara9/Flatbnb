package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.piso.GetAllPropietariosDto;
import com.salesianostriana.flatbnb.service.PropietarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
