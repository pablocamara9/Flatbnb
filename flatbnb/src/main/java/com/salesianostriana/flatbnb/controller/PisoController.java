package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.piso.CreatePisoDto;
import com.salesianostriana.flatbnb.dto.piso.EditPisoDto;
import com.salesianostriana.flatbnb.dto.piso.GetAllPisosDto;
import com.salesianostriana.flatbnb.dto.piso.GetPisoDto;
import com.salesianostriana.flatbnb.model.Piso;
import com.salesianostriana.flatbnb.service.PisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/piso/")
public class PisoController {

    private final PisoService pisoService;

    @GetMapping
    public ResponseEntity<GetAllPisosDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllPisosDto.fromDto(pisoService.findAll()));
    }

    @PostMapping
    public ResponseEntity<GetPisoDto> create(@RequestBody CreatePisoDto dto) {
        Piso piso = pisoService.createPiso(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetPisoDto.of(piso));
    }

    @GetMapping("{id}")
    public GetPisoDto findById(@PathVariable UUID id) {
        return GetPisoDto.of(pisoService.findById(id));
    }

    @PutMapping("{id}")
    public GetPisoDto edit(@PathVariable UUID id, @RequestBody EditPisoDto editPisoDto) {
        return GetPisoDto.of(pisoService.edit(id, editPisoDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        pisoService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
