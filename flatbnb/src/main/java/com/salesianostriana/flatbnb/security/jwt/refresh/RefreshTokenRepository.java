package com.salesianostriana.flatbnb.security.jwt.refresh;

import com.salesianostriana.flatbnb.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.UUID;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {

    @Modifying
    @Transactional
    void deleteByUser(User user);

}
