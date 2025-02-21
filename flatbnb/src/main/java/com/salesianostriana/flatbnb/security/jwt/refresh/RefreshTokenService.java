package com.salesianostriana.flatbnb.security.jwt.refresh;

import com.salesianostriana.flatbnb.dto.UserResponse;
import com.salesianostriana.flatbnb.model.User;
import com.salesianostriana.flatbnb.repository.UserRepository;
import com.salesianostriana.flatbnb.security.jwt.access.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${jwt.refresh.duration}")
    private int durationInMinutes;

    public RefreshToken create(User user) {
        refreshTokenRepository.deleteByUser(user);
        return refreshTokenRepository.save(
                RefreshToken.builder()
                        .user(user)
                        .expireAt(Instant.now().plusSeconds(durationInMinutes * 60))
                        .build()
        );
    }

    public RefreshToken verify(RefreshToken refreshToken) {
        if(refreshToken.getExpireAt().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenException("Token de refresco caducado. Por favor, vuelva a loguearse.");
        }
        return refreshToken;
    }

    public UserResponse refreshToken(String token) {
        return refreshTokenRepository.findById(UUID.fromString(token))
                .map(this::verify)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtService.generateAccessToken(user);
                    RefreshToken refreshToken = this.create(user);
                    return UserResponse.of(user, accessToken, refreshToken.getToken());
                })
                .orElseThrow(() -> new RefreshTokenException("No se ha podido refrescar el token. Por favor, vuelva a loguearse."));
    }

}
