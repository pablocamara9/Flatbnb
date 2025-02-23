package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.*;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.security.jwt.access.JwtService;
import com.salesianostriana.flatbnb.security.jwt.refresh.RefreshToken;
import com.salesianostriana.flatbnb.security.jwt.refresh.RefreshTokenRequest;
import com.salesianostriana.flatbnb.security.jwt.refresh.RefreshTokenService;
import com.salesianostriana.flatbnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

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

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.username(),
                        loginRequestDto.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(user, accessToken, refreshToken.getToken()));
    }

    @PostMapping("auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(refreshTokenService.refreshToken(token));
    }

    @PostMapping("activate/account")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountRequestDto req) {
        String token = req.token();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(userService.activateAccount(token)));

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
