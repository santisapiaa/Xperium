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

        /* @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req
                                                .requestMatchers("/auth/**").permitAll()
                                                .requestMatchers("/productos/**").permitAll()
                                                //.requestMatchers("/productos/**").hasAnyAuthority(Role.VENDEDOR.name())
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }*/
        
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/productos/**").hasAnyAuthority("ROLE_COMPRADOR", "ROLE_VENDEDOR")
                        .requestMatchers(HttpMethod.POST, "/productos/**").hasAuthority("ROLE_VENDEDOR")
                        .requestMatchers(HttpMethod.PUT, "/productos/**").hasAuthority("ROLE_VENDEDOR")
                        .requestMatchers(HttpMethod.DELETE, "/productos/**").hasAuthority("ROLE_VENDEDOR")
                        .requestMatchers("/productos/**").hasAnyAuthority("ROLE_VENDEDOR")
                        .requestMatchers("/direcciones/**").hasAnyAuthority("ROLE_COMPRADOR")
                        .requestMatchers("/ordenesDeCompra/**").hasAnyAuthority("ROLE_COMPRADOR")
                        .requestMatchers("/detallesOrdenDeCompra/**").hasAnyAuthority("ROLE_COMPRADOR")
                        .requestMatchers(HttpMethod.GET, "/categorias/**").hasAnyAuthority("ROLE_VENDEDOR", "ROLE_COMPRADOR")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }
}
