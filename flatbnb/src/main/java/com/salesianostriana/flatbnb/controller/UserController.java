package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.*;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<GetAllUsersDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllUsersDto.fromDto(userService.findAll()));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserDto createUserDto) {
        User user = userService.createUser(createUserDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(user));
    }

    @GetMapping("{id}")
    public GetUserDto findById(@PathVariable UUID id) {
        return GetUserDto.of(userService.findById(id));
    }

    @PutMapping("{id}")
    public GetUserDto edit(@PathVariable UUID id, @RequestBody EditUserDto editUserDto) {
        return GetUserDto.of(userService.edit(id, editUserDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
