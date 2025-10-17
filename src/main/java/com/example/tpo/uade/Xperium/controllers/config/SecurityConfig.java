package com.example.tpo.uade.Xperium.controllers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req  
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/direcciones/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/productos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/productos/**").hasAuthority("ROLE_VENDEDOR")
                .requestMatchers(HttpMethod.PUT, "/productos/**").hasAuthority("ROLE_VENDEDOR")
                .requestMatchers(HttpMethod.DELETE, "/productos/**").hasAuthority("ROLE_VENDEDOR")
                .requestMatchers("/productos/**").hasAnyAuthority("ROLE_VENDEDOR")
                .requestMatchers("/ordenesDeCompra/**").hasAnyAuthority("ROLE_COMPRADOR", "ROLE_VENDEDOR")
                .requestMatchers("/detallesOrdenDeCompra/**").hasAnyAuthority("ROLE_COMPRADOR", "ROLE_VENDEDOR")
                .requestMatchers(HttpMethod.GET, "/categorias/**").hasAnyAuthority("ROLE_VENDEDOR", "ROLE_COMPRADOR","ROLE_ADMIN")
                .requestMatchers("/categorias/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated())
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
