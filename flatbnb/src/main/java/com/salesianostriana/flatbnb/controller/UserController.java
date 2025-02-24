package com.salesianostriana.flatbnb.controller;

import com.salesianostriana.flatbnb.dto.account.ActivateAccountRequestDto;
import com.salesianostriana.flatbnb.dto.account.LoginRequestDto;
import com.salesianostriana.flatbnb.dto.user.*;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.security.jwt.access.JwtService;
import com.salesianostriana.flatbnb.security.jwt.refresh.RefreshToken;
import com.salesianostriana.flatbnb.security.jwt.refresh.RefreshTokenRequest;
import com.salesianostriana.flatbnb.security.jwt.refresh.RefreshTokenService;
import com.salesianostriana.flatbnb.service.UserService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
@Tag(name = "User", description = "El controlador de los usuarios, para gestionar todas las operaciones relacionadas con ellos")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Obtiene todos los usuarios y los devuelve en forma de listado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los usuarios",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = User.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "c9ea3faf-4dd1-4286-bfe5-a3b80fc119ba",
                                                "username": "pablo",
                                                "nombre": "Pablo",
                                                "apellidos": "Camara Garcia",
                                                "email": "camara.gapab23@triana.salesianos.edu",
                                                "telefono": "660232323",
                                                "role": "[USER]",
                                                "enabled": false,
                                                "createdAt": "2025-02-23T23:25:21.021083Z"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron los usuarios"
            )
    })
    @GetMapping
    public ResponseEntity<GetAllUsersDto> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(GetAllUsersDto.fromDto(userService.findAll()));
    }

    @Operation(summary = "Crea un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Usuario creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para crear el usuario",
                    content = @Content)
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserDto createUserDto) {
        User user = userService.createUser(createUserDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(user));
    }

    @Operation(summary = "Iniciar sesión con mi cuenta de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Acceso concedido",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para acceder como usuario",
                    content = @Content)
    })
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

    @Operation(summary = "Crea y devuelve un token de refresco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Token de refresco creado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefreshToken.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para crear el nuevo token de refresco",
                    content = @Content)
    })
    @PostMapping("auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(refreshTokenService.refreshToken(token));
    }

    @Operation(summary = "Activa la cuenta de usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Usuario activado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Datos inválidos para activar la cuenta de usuario",
                    content = @Content)
    })
    @PostMapping("activate/account")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountRequestDto req) {
        String token = req.token();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(userService.activateAccount(token)));

    }

    @Operation(summary = "Obtiene un usuario por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el usuario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el usuario con el id (id proporcionado)",
                    content = @Content)
    })
    @GetMapping("{id}")
    public GetUserDto findById(@PathVariable UUID id) {
        return GetUserDto.of(userService.findById(id));
    }

    @Operation(summary = "Edita un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Usuario actualizado con éxito",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el usuario con el id (id proporcionado)",
                    content = @Content)
    })
    @PutMapping("{id}")
    public GetUserDto edit(@PathVariable UUID id, @RequestBody EditUserDto editUserDto) {
        return GetUserDto.of(userService.edit(id, editUserDto));
    }

    @Operation(summary = "Elimina un usuario buscándolo por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Usuario eliminado con éxito",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se encontró el usuario con el id (id proporcionado)",
                    content = @Content)
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
