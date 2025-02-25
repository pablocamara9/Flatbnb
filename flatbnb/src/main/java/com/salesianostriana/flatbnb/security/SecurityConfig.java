package com.salesianostriana.flatbnb.security;

import com.salesianostriana.flatbnb.security.exceptionhandling.JwtAccessDeniedHandler;
import com.salesianostriana.flatbnb.security.exceptionhandling.JwtAuthenticationEntryPoint;
import com.salesianostriana.flatbnb.security.jwt.access.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        AuthenticationManager authenticationManager =
                authenticationManagerBuilder.authenticationProvider(authenticationProvider())
                        .build();

        return authenticationManager;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();

        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder);

        return p;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(excepz -> excepz
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );

        http.authorizeHttpRequests(authz -> authz
                //USUARIOS
                .requestMatchers(HttpMethod.GET, "user/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "user/auth/register", "user/activate/account", "/user/auth/login", "/user/auth/refresh/token").permitAll()
                .requestMatchers(HttpMethod.PUT, "user/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "user/**").hasRole("ADMIN")
                //PISOS
                .requestMatchers(HttpMethod.GET, "piso/**").permitAll()
                .requestMatchers(HttpMethod.POST, "piso/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .requestMatchers(HttpMethod.PUT, "piso/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .requestMatchers(HttpMethod.DELETE, "piso/**").hasAnyRole( "ADMIN","PROPIETARIO")
                //PROPIETARIOS
                .requestMatchers(HttpMethod.GET, "propietario/**").permitAll()
                .requestMatchers(HttpMethod.POST, "propietario/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "propietario/**").hasAnyRole("ADMIN", "PROPIETARIO")
                .requestMatchers(HttpMethod.DELETE, "propietario/**").hasAnyRole("ADMIN", "PROPIETARIO")
                //OTRAS COSAS
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable())
        );

        return http.build();

    }

}
