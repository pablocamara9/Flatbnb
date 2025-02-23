package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.CreateUserDto;
import com.salesianostriana.flatbnb.dto.GetAllUsersDto;
import com.salesianostriana.flatbnb.dto.UserResponse;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
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

}
