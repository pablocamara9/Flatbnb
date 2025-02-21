package com.salesianostriana.flatbnb.security.jwt.refresh;

import io.jsonwebtoken.JwtException;

public class RefreshTokenException extends JwtException {

    public RefreshTokenException(String s) {
        super(s);
    }
}
